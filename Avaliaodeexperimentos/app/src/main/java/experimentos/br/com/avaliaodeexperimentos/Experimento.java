package experimentos.br.com.avaliaodeexperimentos;

import java.io.Serializable;

//Classe modelo de um experimento, implementa Serializable para passar o objeto por bundle
public class Experimento implements Serializable {
    private Integer id;
    private String nome;
    private String data;
    private Integer totalAmostras;
    private Integer totalBlocos;
    private Integer amostrasAdicionadas;
    private Integer blocosAdicionados;
    private String proprietario;

    public Experimento() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getTotalAmostras() {
        return totalAmostras;
    }

    public void setTotalAmostras(Integer totalAmostras) {
        this.totalAmostras = totalAmostras;
    }

    public Integer getTotalBlocos() {
        return totalBlocos;
    }

    public void setTotalBlocos(Integer totalBlocos) {
        this.totalBlocos = totalBlocos;
    }

    public Integer getAmostrasAdicionadas() {
        return amostrasAdicionadas;
    }

    public void setAmostrasAdicionadas(Integer amostrasAdicionadas) {
        this.amostrasAdicionadas = amostrasAdicionadas;
    }

    public Integer getBlocosAdicionados() {
        return blocosAdicionados;
    }

    public void setBlocosAdicionados(Integer blocosAdicionados) {
        this.blocosAdicionados = blocosAdicionados;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Experimento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", totalAmostras=" + totalAmostras +
                ", totalBlocos=" + totalBlocos +
                ", amostrasAdicionadas=" + amostrasAdicionadas +
                ", blocosAdicionados=" + blocosAdicionados +
                ", proprietario='" + proprietario + '\'' +
                '}';
    }
}
