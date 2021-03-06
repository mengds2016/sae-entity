/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.nex.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Renzo
 */
@Entity
@Table(name = "cer_especie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CerEspecie.findAll", query = "SELECT c FROM CerEspecie c"),
    @NamedQuery(name = "CerEspecie.findByIdEspecie", query = "SELECT c FROM CerEspecie c WHERE c.idEspecie = :idEspecie"),
    @NamedQuery(name = "CerEspecie.findByDescripcion", query = "SELECT c FROM CerEspecie c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CerEspecie.findByNombre", query = "SELECT c FROM CerEspecie c WHERE c.nombre = :nombre")})
public class CerEspecie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_especie")
    private Long idEspecie;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "variedad_espiece", joinColumns = {
        @JoinColumn(name = "id_especie", referencedColumnName = "id_especie")}, inverseJoinColumns = {
        @JoinColumn(name = "id_variedad", referencedColumnName = "id_variedead")})
    @ManyToMany
    private List<CerVariedad> cerVariedadList;

    public CerEspecie() {
    }

    public CerEspecie(Long idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Long getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Long idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<CerVariedad> getCerVariedadList() {
        return cerVariedadList;
    }

    public void setCerVariedadList(List<CerVariedad> cerVariedadList) {
        this.cerVariedadList = cerVariedadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspecie != null ? idEspecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CerEspecie)) {
            return false;
        }
        CerEspecie other = (CerEspecie) object;
        if ((this.idEspecie == null && other.idEspecie != null) || (this.idEspecie != null && !this.idEspecie.equals(other.idEspecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.nex.entity.CerEspecie[ idEspecie=" + idEspecie + " ]";
    }
    
}
