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
@Table(name = "cer_variedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CerVariedad.findAll", query = "SELECT c FROM CerVariedad c"),
    @NamedQuery(name = "CerVariedad.findByIdVariedead", query = "SELECT c FROM CerVariedad c WHERE c.idVariedead = :idVariedead"),
    @NamedQuery(name = "CerVariedad.findByDescripcion", query = "SELECT c FROM CerVariedad c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CerVariedad.findByNombre", query = "SELECT c FROM CerVariedad c WHERE c.nombre = :nombre")})
public class CerVariedad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_variedead")
    private Long idVariedead;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "cerVariedadList")
    private List<CerEspecie> cerEspecieList;

    public CerVariedad() {
    }

    public CerVariedad(Long idVariedead) {
        this.idVariedead = idVariedead;
    }

    public Long getIdVariedead() {
        return idVariedead;
    }

    public void setIdVariedead(Long idVariedead) {
        this.idVariedead = idVariedead;
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
    public List<CerEspecie> getCerEspecieList() {
        return cerEspecieList;
    }

    public void setCerEspecieList(List<CerEspecie> cerEspecieList) {
        this.cerEspecieList = cerEspecieList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVariedead != null ? idVariedead.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CerVariedad)) {
            return false;
        }
        CerVariedad other = (CerVariedad) object;
        if ((this.idVariedead == null && other.idVariedead != null) || (this.idVariedead != null && !this.idVariedead.equals(other.idVariedead))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.nex.entity.CerVariedad[ idVariedead=" + idVariedead + " ]";
    }
    
}
