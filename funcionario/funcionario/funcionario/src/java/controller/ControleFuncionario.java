package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.EmpresaDao;
import model.EmpresaDao.SaveResult;
import model.Funcionario;

@WebServlet(name = "ControleFuncionario", urlPatterns = {"/ControleFuncionario"})
public class ControleFuncionario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String flag, mensagem;
        flag = request.getParameter("flag");
        if (flag.equals("cad_fun")) {
            //Aqui faremos a parte de cadastro de funcionário
            String nome, endereco, bairro, cidade, cargo;
            int matricula;
            double salario;

            nome = request.getParameter("nome");
            endereco = request.getParameter("endereco");
            bairro = request.getParameter("bairro");
            cidade = request.getParameter("cidade");
            cargo = request.getParameter("cargo");
            salario = Double.parseDouble(request.getParameter("salario"));
            matricula = Integer.parseInt(request.getParameter("matricula"));

            //Empacotar em um objeto da classe Funcionario.java
            Funcionario fun = new Funcionario();
            fun.setNome(nome);
            fun.setEndereco(endereco);
            fun.setBairro(bairro);
            fun.setCidade(cidade);
            fun.setCargo(cargo);
            fun.setSalario(salario);
            fun.setMatricula(matricula);
            // Usando o metodo salvar da EmpresaDao()
            SaveResult resultado = new EmpresaDao().salvar(fun);
            // Gerar a mensagem pro usuário
            if (resultado == SaveResult.SUCESSO) {
                mensagem = "Funcionário " + nome + " salvo com sucesso!";
            } else if (resultado == SaveResult.CHAVEDUPLICADA) {
                mensagem = "Funcionário com matricula " + matricula + " já cadastrado!";
            } else {
                mensagem = "Erro: Entre em contato com o suporte";
            }
            //Exibir a mensagem para o usuário
            request.setAttribute("mensagem", mensagem);
            RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
            disp.forward(request, response);
            //listagem
        } else if (flag.equals("listar")) {
            List<Funcionario> listaFuncionarios = new EmpresaDao().listar("Funcionario.findAll", Funcionario.class);
            request.setAttribute("listaFuncionarios", listaFuncionarios);
            RequestDispatcher disp = request.getRequestDispatcher("mostrar_funcionarios.jsp");
            disp.forward(request, response);
        } //exclusão
        else if (flag.equals("exc_fun")){
            String nome = request.getParameter("nome");
            int matricula = Integer.parseInt(request.getParameter("matricula"));
            int retorno = new EmpresaDao().excluir(matricula, Funcionario.class);
            switch (retorno) {
                case 1:
                    mensagem = "Funcionario excluído com sucesso";
                    break;
                case 2:
                    mensagem = "Funcionario não cadastrado";
                    break;
                default:
                    mensagem = "Erro encontrado. Entre em contato com o suporte";
                    break;
            }
            request.setAttribute("mensagem", mensagem);
            request.getRequestDispatcher("mensagens.jsp").forward(request, response);
        } else if (flag.equals("buscar_alt_fun")) {
            int matricula = Integer.parseInt(request.getParameter("matricula"));
            Funcionario fun = new EmpresaDao().buscar(Funcionario.class, matricula);
            if (fun == null) { 
                request.setAttribute("mensagem", "Funcionario não encontrado " + matricula);
                request.getRequestDispatcher("mensagens.jsp").forward(request, response);
            } else { 
                request.setAttribute("fun", fun);
                request.getRequestDispatcher("altfun.jsp").forward(request, response);
            }
        } //alterar
        else if (flag.equals("alt_fun")) {
            String nome, endereco, bairro, cidade,cargo;
            double salario;
            int matricula;

            nome = request.getParameter("nome");
            endereco = request.getParameter("endereco");
            bairro = request.getParameter("bairro");
            cidade = request.getParameter("cidade");
            cargo = request.getParameter("cargo");
            salario = Double.parseDouble(request.getParameter("salario"));
            
            try {
                salario = Double.parseDouble(request.getParameter("salario"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }
            matricula = Integer.parseInt(request.getParameter("matricula"));

            Funcionario fun = new Funcionario();
            fun.setNome(nome);
            fun.setEndereco(endereco);
            fun.setBairro(bairro);
            fun.setCidade(cidade);
            fun.setCargo(cargo);
            fun.setSalario(salario);
            fun.setMatricula(matricula);
            
            int resultado;
            try {
                resultado = new EmpresaDao().alterar(fun);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            if (resultado == 1) {
                mensagem = "Alteração no funcionário " + nome + " realizada com sucesso!";
            } else {
                mensagem = "Erro ao tentar alterar os dados. Entre em contato com o suporte.";
            }

            request.setAttribute("mensagem", mensagem);
            request.getRequestDispatcher("mensagens.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
