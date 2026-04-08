public class Usuario {

    private final String nome;
    private final String cpf;
    private final String telefone;
    private final String email;
    private final boolean anonimo;

    public Usuario(String nome, String cpf, String telefone, String email, boolean anonimo) {

        this.anonimo = anonimo;

        if (anonimo) {
            this.nome = "ANONIMO";
            this.cpf = null;
            this.telefone = null;
            this.email = null;
        } else {
            this.nome = nome;
            this.cpf = cpf;
            this.telefone = telefone;
            this.email = email;
        }
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public String getCpfMascarado() {

        if (cpf == null) return "N/A";

        return "***.***.***-" + cpf.substring(cpf.length() - 2);
    }

    public String getNome()      { return nome; }
    public String getCpf()       { return cpf; }
    public String getTelefone()  { return telefone; }
    public String getEmail()     { return email; }

}