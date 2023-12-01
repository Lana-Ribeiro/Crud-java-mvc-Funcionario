<%@page import="model.Funcionario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("listaFuncionarios");
        %>
        <table border='1'>    
            <tr>
                <th> Matricula </th>
                <th> Nome </th>
                <th> Endereco </th>
                <th> Bairro </th>
                <th> Cidade </th>
                <th> Cargo </th>
                <th> Salario </th>
            </tr>
            <%        
                for (Funcionario fun : funcionarios) {
            %>
            <tr>
                <td> <%= fun.getMatricula()%> </td>
                <td> <%= fun.getNome()%> </td>
                <td> <%= fun.getEndereco()%> </td>      
                <td> <%= fun.getBairro()%> </td>
                <td> <%= fun.getCidade()%> </td>
                <td> <%= fun.getCargo()%> </td>
                <td> <%= fun.getSalario()%> </td>
            </tr>

            <%
                }
            %>

        </table>
    </body>
</html>
