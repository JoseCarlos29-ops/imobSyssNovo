package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TipoImovelController {

    private static final String API = "http://localhost:8080";

    // Login do administrador da empresa (o tipo de imovel pertence a empresa dele)
    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextArea txtLista;

    private record Resposta(int codigo, String corpo) {
    }

    // Salvar: sem ID cadastra um novo tipo, com ID atualiza o tipo informado
    @FXML
    public void salvarTipo(ActionEvent event) {

        try {
            String token = obterToken();

            String jsonRequest = String.format("{\n" +
                            "  \"nome\": \"%s\",\n" +
                            "  \"descricao\": \"%s\"\n" +
                            "}", escapar(txtNome.getText()),
                    escapar(txtDescricao.getText()));

            boolean edicao = !txtId.getText().isBlank();
            String url = API + "/tipos-imovel" + (edicao ? "/" + txtId.getText().trim() : "");

            Resposta resposta = executaMetodoAPI(url, jsonRequest, edicao ? "PUT" : "POST", token);

            if (resposta.codigo() == 200 || resposta.codigo() == 201) {
                showMessage(Alert.AlertType.INFORMATION, "Tipo de imovel salvo com sucesso!");
                limparCampos();
                atualizarLista(token);
            } else {
                showMessage(Alert.AlertType.ERROR, mensagemErro(resposta, "Erro ao salvar tipo de imovel!"));
            }

        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void listarTipos(ActionEvent event) {

        try {
            atualizarLista(obterToken());
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void excluirTipo(ActionEvent event) {

        try {
            if (txtId.getText().isBlank()) {
                showMessage(Alert.AlertType.ERROR, "Informe o ID do tipo que deseja excluir!");
                return;
            }

            String token = obterToken();

            Resposta resposta = executaMetodoAPI(API + "/tipos-imovel/" + txtId.getText().trim(), null, "DELETE", token);

            if (resposta.codigo() == 200) {
                showMessage(Alert.AlertType.INFORMATION, "Tipo de imovel excluido com sucesso!");
                limparCampos();
                atualizarLista(token);
            } else if (resposta.codigo() == 404) {
                showMessage(Alert.AlertType.ERROR, "Tipo de imovel nao encontrado!");
            } else {
                showMessage(Alert.AlertType.ERROR, mensagemErro(resposta, "Erro ao excluir tipo de imovel!"));
            }

        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    // Faz o login na API e devolve o token JWT
    private String obterToken() throws Exception {

        if (txtEmail.getText().isBlank() || txtSenha.getText().isBlank()) {
            throw new Exception("Informe o email e a senha do administrador!");
        }

        String jsonLogin = String.format("{\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"senha\": \"%s\"\n" +
                        "}", escapar(txtEmail.getText()),
                escapar(txtSenha.getText()));

        Resposta resposta = executaMetodoAPI(API + "/auth/login", jsonLogin, "POST", null);

        if (resposta.codigo() != 200) {
            throw new Exception("Email ou senha invalidos!");
        }

        Matcher matcher = Pattern.compile("\"token\"\\s*:\\s*\"([^\"]+)\"").matcher(resposta.corpo());
        if (!matcher.find()) {
            throw new Exception("Nao foi possivel obter o token de acesso!");
        }

        return matcher.group(1);
    }

    private void atualizarLista(String token) throws Exception {

        Resposta resposta = executaMetodoAPI(API + "/tipos-imovel", null, "GET", token);

        if (resposta.codigo() != 200) {
            throw new Exception(mensagemErro(resposta, "Erro ao listar tipos de imovel!"));
        }

        StringBuilder lista = new StringBuilder();
        Matcher matcher = Pattern.compile("\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"nome\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"")
                .matcher(resposta.corpo());

        while (matcher.find()) {
            lista.append(matcher.group(1)).append(" - ").append(matcher.group(2)).append("\n");
        }

        txtLista.setText(lista.length() == 0 ? "Nenhum tipo cadastrado." : lista.toString());
    }

    private Resposta executaMetodoAPI(String url, String json, String protocoloHttp, String token)
            throws Exception {

        URL urlAPI = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocoloHttp);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        if (token != null) {
            connection.setRequestProperty("Authorization", "Bearer " + token);
        }

        if (json != null && !json.isEmpty()) {
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }
        }

        int codigo = connection.getResponseCode();

        InputStream fluxo = codigo >= 400 ? connection.getErrorStream() : connection.getInputStream();
        String corpo = fluxo == null ? "" : new String(fluxo.readAllBytes(), StandardCharsets.UTF_8);

        return new Resposta(codigo, corpo);
    }

    // Mensagem de regra de negocio devolvida pelo back (ex.: "Tipo de imovel em uso ...")
    private String mensagemErro(Resposta resposta, String mensagemPadrao) {
        if (resposta.codigo() == 400 && !resposta.corpo().isBlank()) {
            return resposta.corpo();
        }
        return mensagemPadrao;
    }

    private String escapar(String texto) {
        return texto == null ? "" : texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtDescricao.clear();
    }

    private void showMessage(Alert.AlertType type, String msg) {
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

}
