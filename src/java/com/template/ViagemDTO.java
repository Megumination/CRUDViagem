package model.dto;

import java.util.Date;

public class ViagemDTO {
    private int id;
    private String destino;
    private Date dataIda;
    private Date dataVolta;
    private double preco;
    private String observacoes;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Date getDataIda() { return dataIda; }
    public void setDataIda(Date dataIda) { this.dataIda = dataIda; }

    public Date getDataVolta() { return dataVolta; }
    public void setDataVolta(Date dataVolta) { this.dataVolta = dataVolta; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}