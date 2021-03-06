/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.nex.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Renzo
 */
@Entity
@Table(name = "fle_destino")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FleDestino.findAll", query = "SELECT f FROM FleDestino f"),
    @NamedQuery(name = "FleDestino.findByIdDestino", query = "SELECT f FROM FleDestino f WHERE f.idDestino = :idDestino"),
    @NamedQuery(name = "FleDestino.findByCodigo", query = "SELECT f FROM FleDestino f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "FleDestino.findByDireccion", query = "SELECT f FROM FleDestino f WHERE f.direccion = :direccion"),
    @NamedQuery(name = "FleDestino.findByObservacion", query = "SELECT f FROM FleDestino f WHERE f.observacion = :observacion")})
public class FleDestino implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_destino")
    private Long idDestino;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "observacion")
    private String observacion;

    public FleDestino() {
    }

    public FleDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public Long getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDestino != null ? idDestino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FleDestino)) {
            return false;
        }
        FleDestino other = (FleDestino) object;
        if ((this.idDestino == null && other.idDestino != null) || (this.idDestino != null && !this.idDestino.equals(other.idDestino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.nex.entity.FleDestino[ idDestino=" + idDestino + " ]";
    }
    
}
