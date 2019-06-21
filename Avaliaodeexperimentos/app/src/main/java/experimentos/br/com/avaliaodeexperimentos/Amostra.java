package experimentos.br.com.avaliaodeexperimentos;

public class Amostra {
    private Integer id;
    private Double altura;
    private Integer milimetroColmo;
    private Double alturaInsercao;
    private Integer numeroBloco;
    private Experimento experimento;
    private String proprietario;

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Integer getMilimetroColmo() {
        return milimetroColmo;
    }

    public void setMilimetroColmo(Integer milimetroColmo) {
        this.milimetroColmo = milimetroColmo;
    }

    public Double getAlturaInsercao() {
        return alturaInsercao;
    }

    public void setAlturaInsercao(Double alturaInsercao) {
        this.alturaInsercao = alturaInsercao;
    }

    public Experimento getExperimento() {
        return experimento;
    }

    public void setExperimento(Experimento experimento) {
        this.experimento = experimento;
    }

    public Integer getNumeroBloco() {
        return numeroBloco;
    }

    public void setNumeroBloco(Integer numeroBloco) {
        this.numeroBloco = numeroBloco;
    }

    @Override
    public String toString() {
        return "Amostra{" +
                "id=" + id +
                ", altura=" + altura +
                ", milimetroColmo=" + milimetroColmo +
                ", alturaInsercao=" + alturaInsercao +
                ", numeroBloco=" + numeroBloco +
                ", experimento=" + experimento +
                '}';
    }
}
