/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.nex.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ar.nex.entity.Equipo;
import java.util.ArrayList;
import java.util.List;
import ar.nex.entity.Empresa;
import ar.nex.entity.EquipoModelo;
import ar.nex.entity.RepuestoStockDetalle;
import ar.nex.entity.Pedido;
import ar.nex.entity.Repuesto;
import ar.nex.jpa.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Renzo
 */
public class RepuestoJpaController implements Serializable {

    public RepuestoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Repuesto repuesto) {
        if (repuesto.getEquipoList() == null) {
            repuesto.setEquipoList(new ArrayList<Equipo>());
        }
        if (repuesto.getEmpresaList() == null) {
            repuesto.setEmpresaList(new ArrayList<Empresa>());
        }
        if (repuesto.getModeloList() == null) {
            repuesto.setModeloList(new ArrayList<EquipoModelo>());
        }
        if (repuesto.getRepuestoStockDetalleList() == null) {
            repuesto.setRepuestoStockDetalleList(new ArrayList<RepuestoStockDetalle>());
        }
        if (repuesto.getPedidoList() == null) {
            repuesto.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Equipo> attachedEquipoList = new ArrayList<Equipo>();
            for (Equipo equipoListEquipoToAttach : repuesto.getEquipoList()) {
                equipoListEquipoToAttach = em.getReference(equipoListEquipoToAttach.getClass(), equipoListEquipoToAttach.getIdEquipo());
                attachedEquipoList.add(equipoListEquipoToAttach);
            }
            repuesto.setEquipoList(attachedEquipoList);
            List<Empresa> attachedEmpresaList = new ArrayList<Empresa>();
            for (Empresa empresaListEmpresaToAttach : repuesto.getEmpresaList()) {
                empresaListEmpresaToAttach = em.getReference(empresaListEmpresaToAttach.getClass(), empresaListEmpresaToAttach.getIdEmpresa());
                attachedEmpresaList.add(empresaListEmpresaToAttach);
            }
            repuesto.setEmpresaList(attachedEmpresaList);
            List<EquipoModelo> attachedModeloList = new ArrayList<EquipoModelo>();
            for (EquipoModelo modeloListEquipoModeloToAttach : repuesto.getModeloList()) {
                modeloListEquipoModeloToAttach = em.getReference(modeloListEquipoModeloToAttach.getClass(), modeloListEquipoModeloToAttach.getIdModelo());
                attachedModeloList.add(modeloListEquipoModeloToAttach);
            }
            repuesto.setModeloList(attachedModeloList);
            List<RepuestoStockDetalle> attachedRepuestoStockDetalleList = new ArrayList<RepuestoStockDetalle>();
            for (RepuestoStockDetalle repuestoStockDetalleListRepuestoStockDetalleToAttach : repuesto.getRepuestoStockDetalleList()) {
                repuestoStockDetalleListRepuestoStockDetalleToAttach = em.getReference(repuestoStockDetalleListRepuestoStockDetalleToAttach.getClass(), repuestoStockDetalleListRepuestoStockDetalleToAttach.getIdStock());
                attachedRepuestoStockDetalleList.add(repuestoStockDetalleListRepuestoStockDetalleToAttach);
            }
            repuesto.setRepuestoStockDetalleList(attachedRepuestoStockDetalleList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : repuesto.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            repuesto.setPedidoList(attachedPedidoList);
            em.persist(repuesto);
            for (Equipo equipoListEquipo : repuesto.getEquipoList()) {
                equipoListEquipo.getRepuestoList().add(repuesto);
                equipoListEquipo = em.merge(equipoListEquipo);
            }
            for (Empresa empresaListEmpresa : repuesto.getEmpresaList()) {
                empresaListEmpresa.getRepuestoList().add(repuesto);
                empresaListEmpresa = em.merge(empresaListEmpresa);
            }
            for (EquipoModelo modeloListEquipoModelo : repuesto.getModeloList()) {
                modeloListEquipoModelo.getRepuestoList().add(repuesto);
                modeloListEquipoModelo = em.merge(modeloListEquipoModelo);
            }
            for (RepuestoStockDetalle repuestoStockDetalleListRepuestoStockDetalle : repuesto.getRepuestoStockDetalleList()) {
                Repuesto oldRepuestoOfRepuestoStockDetalleListRepuestoStockDetalle = repuestoStockDetalleListRepuestoStockDetalle.getRepuesto();
                repuestoStockDetalleListRepuestoStockDetalle.setRepuesto(repuesto);
                repuestoStockDetalleListRepuestoStockDetalle = em.merge(repuestoStockDetalleListRepuestoStockDetalle);
                if (oldRepuestoOfRepuestoStockDetalleListRepuestoStockDetalle != null) {
                    oldRepuestoOfRepuestoStockDetalleListRepuestoStockDetalle.getRepuestoStockDetalleList().remove(repuestoStockDetalleListRepuestoStockDetalle);
                    oldRepuestoOfRepuestoStockDetalleListRepuestoStockDetalle = em.merge(oldRepuestoOfRepuestoStockDetalleListRepuestoStockDetalle);
                }
            }
            for (Pedido pedidoListPedido : repuesto.getPedidoList()) {
                Repuesto oldRepuestoOfPedidoListPedido = pedidoListPedido.getRepuesto();
                pedidoListPedido.setRepuesto(repuesto);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldRepuestoOfPedidoListPedido != null) {
                    oldRepuestoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldRepuestoOfPedidoListPedido = em.merge(oldRepuestoOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Repuesto repuesto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Repuesto persistentRepuesto = em.find(Repuesto.class, repuesto.getIdRepuesto());
            List<Equipo> equipoListOld = persistentRepuesto.getEquipoList();
            List<Equipo> equipoListNew = repuesto.getEquipoList();
            List<Empresa> empresaListOld = persistentRepuesto.getEmpresaList();
            List<Empresa> empresaListNew = repuesto.getEmpresaList();
            List<EquipoModelo> modeloListOld = persistentRepuesto.getModeloList();
            List<EquipoModelo> modeloListNew = repuesto.getModeloList();
            List<RepuestoStockDetalle> repuestoStockDetalleListOld = persistentRepuesto.getRepuestoStockDetalleList();
            List<RepuestoStockDetalle> repuestoStockDetalleListNew = repuesto.getRepuestoStockDetalleList();
            List<Pedido> pedidoListOld = persistentRepuesto.getPedidoList();
            List<Pedido> pedidoListNew = repuesto.getPedidoList();
            List<Equipo> attachedEquipoListNew = new ArrayList<Equipo>();
            for (Equipo equipoListNewEquipoToAttach : equipoListNew) {
                equipoListNewEquipoToAttach = em.getReference(equipoListNewEquipoToAttach.getClass(), equipoListNewEquipoToAttach.getIdEquipo());
                attachedEquipoListNew.add(equipoListNewEquipoToAttach);
            }
            equipoListNew = attachedEquipoListNew;
            repuesto.setEquipoList(equipoListNew);
            List<Empresa> attachedEmpresaListNew = new ArrayList<Empresa>();
            for (Empresa empresaListNewEmpresaToAttach : empresaListNew) {
                empresaListNewEmpresaToAttach = em.getReference(empresaListNewEmpresaToAttach.getClass(), empresaListNewEmpresaToAttach.getIdEmpresa());
                attachedEmpresaListNew.add(empresaListNewEmpresaToAttach);
            }
            empresaListNew = attachedEmpresaListNew;
            repuesto.setEmpresaList(empresaListNew);
            List<EquipoModelo> attachedModeloListNew = new ArrayList<EquipoModelo>();
            for (EquipoModelo modeloListNewEquipoModeloToAttach : modeloListNew) {
                modeloListNewEquipoModeloToAttach = em.getReference(modeloListNewEquipoModeloToAttach.getClass(), modeloListNewEquipoModeloToAttach.getIdModelo());
                attachedModeloListNew.add(modeloListNewEquipoModeloToAttach);
            }
            modeloListNew = attachedModeloListNew;
            repuesto.setModeloList(modeloListNew);
            List<RepuestoStockDetalle> attachedRepuestoStockDetalleListNew = new ArrayList<RepuestoStockDetalle>();
            for (RepuestoStockDetalle repuestoStockDetalleListNewRepuestoStockDetalleToAttach : repuestoStockDetalleListNew) {
                repuestoStockDetalleListNewRepuestoStockDetalleToAttach = em.getReference(repuestoStockDetalleListNewRepuestoStockDetalleToAttach.getClass(), repuestoStockDetalleListNewRepuestoStockDetalleToAttach.getIdStock());
                attachedRepuestoStockDetalleListNew.add(repuestoStockDetalleListNewRepuestoStockDetalleToAttach);
            }
            repuestoStockDetalleListNew = attachedRepuestoStockDetalleListNew;
            repuesto.setRepuestoStockDetalleList(repuestoStockDetalleListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            repuesto.setPedidoList(pedidoListNew);
            repuesto = em.merge(repuesto);
            for (Equipo equipoListOldEquipo : equipoListOld) {
                if (!equipoListNew.contains(equipoListOldEquipo)) {
                    equipoListOldEquipo.getRepuestoList().remove(repuesto);
                    equipoListOldEquipo = em.merge(equipoListOldEquipo);
                }
            }
            for (Equipo equipoListNewEquipo : equipoListNew) {
                if (!equipoListOld.contains(equipoListNewEquipo)) {
                    equipoListNewEquipo.getRepuestoList().add(repuesto);
                    equipoListNewEquipo = em.merge(equipoListNewEquipo);
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    empresaListOldEmpresa.getRepuestoList().remove(repuesto);
                    empresaListOldEmpresa = em.merge(empresaListOldEmpresa);
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    empresaListNewEmpresa.getRepuestoList().add(repuesto);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                }
            }
            for (EquipoModelo modeloListOldEquipoModelo : modeloListOld) {
                if (!modeloListNew.contains(modeloListOldEquipoModelo)) {
                    modeloListOldEquipoModelo.getRepuestoList().remove(repuesto);
                    modeloListOldEquipoModelo = em.merge(modeloListOldEquipoModelo);
                }
            }
            for (EquipoModelo modeloListNewEquipoModelo : modeloListNew) {
                if (!modeloListOld.contains(modeloListNewEquipoModelo)) {
                    modeloListNewEquipoModelo.getRepuestoList().add(repuesto);
                    modeloListNewEquipoModelo = em.merge(modeloListNewEquipoModelo);
                }
            }
            for (RepuestoStockDetalle repuestoStockDetalleListOldRepuestoStockDetalle : repuestoStockDetalleListOld) {
                if (!repuestoStockDetalleListNew.contains(repuestoStockDetalleListOldRepuestoStockDetalle)) {
                    repuestoStockDetalleListOldRepuestoStockDetalle.setRepuesto(null);
                    repuestoStockDetalleListOldRepuestoStockDetalle = em.merge(repuestoStockDetalleListOldRepuestoStockDetalle);
                }
            }
            for (RepuestoStockDetalle repuestoStockDetalleListNewRepuestoStockDetalle : repuestoStockDetalleListNew) {
                if (!repuestoStockDetalleListOld.contains(repuestoStockDetalleListNewRepuestoStockDetalle)) {
                    Repuesto oldRepuestoOfRepuestoStockDetalleListNewRepuestoStockDetalle = repuestoStockDetalleListNewRepuestoStockDetalle.getRepuesto();
                    repuestoStockDetalleListNewRepuestoStockDetalle.setRepuesto(repuesto);
                    repuestoStockDetalleListNewRepuestoStockDetalle = em.merge(repuestoStockDetalleListNewRepuestoStockDetalle);
                    if (oldRepuestoOfRepuestoStockDetalleListNewRepuestoStockDetalle != null && !oldRepuestoOfRepuestoStockDetalleListNewRepuestoStockDetalle.equals(repuesto)) {
                        oldRepuestoOfRepuestoStockDetalleListNewRepuestoStockDetalle.getRepuestoStockDetalleList().remove(repuestoStockDetalleListNewRepuestoStockDetalle);
                        oldRepuestoOfRepuestoStockDetalleListNewRepuestoStockDetalle = em.merge(oldRepuestoOfRepuestoStockDetalleListNewRepuestoStockDetalle);
                    }
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setRepuesto(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Repuesto oldRepuestoOfPedidoListNewPedido = pedidoListNewPedido.getRepuesto();
                    pedidoListNewPedido.setRepuesto(repuesto);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldRepuestoOfPedidoListNewPedido != null && !oldRepuestoOfPedidoListNewPedido.equals(repuesto)) {
                        oldRepuestoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldRepuestoOfPedidoListNewPedido = em.merge(oldRepuestoOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = repuesto.getIdRepuesto();
                if (findRepuesto(id) == null) {
                    throw new NonexistentEntityException("The repuesto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Repuesto repuesto;
            try {
                repuesto = em.getReference(Repuesto.class, id);
                repuesto.getIdRepuesto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The repuesto with id " + id + " no longer exists.", enfe);
            }
            List<Equipo> equipoList = repuesto.getEquipoList();
            for (Equipo equipoListEquipo : equipoList) {
                equipoListEquipo.getRepuestoList().remove(repuesto);
                equipoListEquipo = em.merge(equipoListEquipo);
            }
            List<Empresa> empresaList = repuesto.getEmpresaList();
            for (Empresa empresaListEmpresa : empresaList) {
                empresaListEmpresa.getRepuestoList().remove(repuesto);
                empresaListEmpresa = em.merge(empresaListEmpresa);
            }
            List<EquipoModelo> modeloList = repuesto.getModeloList();
            for (EquipoModelo modeloListEquipoModelo : modeloList) {
                modeloListEquipoModelo.getRepuestoList().remove(repuesto);
                modeloListEquipoModelo = em.merge(modeloListEquipoModelo);
            }
            List<RepuestoStockDetalle> repuestoStockDetalleList = repuesto.getRepuestoStockDetalleList();
            for (RepuestoStockDetalle repuestoStockDetalleListRepuestoStockDetalle : repuestoStockDetalleList) {
                repuestoStockDetalleListRepuestoStockDetalle.setRepuesto(null);
                repuestoStockDetalleListRepuestoStockDetalle = em.merge(repuestoStockDetalleListRepuestoStockDetalle);
            }
            List<Pedido> pedidoList = repuesto.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setRepuesto(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(repuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Repuesto> findRepuestoEntities() {
        return findRepuestoEntities(true, -1, -1);
    }

    public List<Repuesto> findRepuestoEntities(int maxResults, int firstResult) {
        return findRepuestoEntities(false, maxResults, firstResult);
    }

    private List<Repuesto> findRepuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Repuesto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Repuesto findRepuesto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Repuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getRepuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Repuesto> rt = cq.from(Repuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
