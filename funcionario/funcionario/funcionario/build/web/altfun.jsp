<%@page import="model.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alteração de departamento</title>
    </head>
    <body>
        <%
            Funcionario fun = (Funcionario) request.getAttribute("fun");
        %>
        
        <form method="post" action="ControleFuncionario">
            <input type="hidden" name="flag" value="alt_fun">

            <p>
                <label for="matricula">Matricula*</label>
                <input id="matricula" type="number" name="matricula" placeholder="Coloque sua Matricula" size="80" maxlength="80" required value="<%= fun.getMatricula() %>" hidden>
            </p>
            
            <p>
                <label for="nome">Nome*</label>
                <input id="nome" type="text" name="nome" placeholder="Coloque o seu Nome" size="80" maxlength="80"  required value="<%= fun.getNome() %>">
            </p>

            <p>
                <label for="endereco">Endereco*</label>
                <input id="endereco" type="text" name="endereco" placeholder="Coloque o seu Endereco" size="70" maxlength="70" required value="<%= fun.getEndereco() %>">
            </p>

            <p>
                <label for="bairo">Bairro</label>
                <input id="bairo" type="text" name="bairro" placeholder="Coloque o seu Bairo" size="50" maxlength="50" value="<%= fun.getBairro() %>">
            </p>
            
            <p>
                <label for="cidade">Cidade</label>
                <input id="cidade" type="text" name="cidade" placeholder="Coloque sua cidade" size="40" maxlength="40" value="<%= fun.getCidade() %>">
            </p>
            
            <p>
                <label for="cargo">Cargo</label>
                <input id="cargo" type="text" name="cargo" placeholder="Coloque o seu Cargo" size="40" maxlength="40" value="<%= fun.getCargo() %>">
            </p>

            <p>
                <label for="salario">Salário</label>
                <input id="salario" type="number" name="salario" placeholder="Coloque o seu Salário" step="0.1" value="<%= fun.getSalario() %>">
            </p>

            <input type="submit" value="Salvar Alteração">
        </form>
    </body>
</html>