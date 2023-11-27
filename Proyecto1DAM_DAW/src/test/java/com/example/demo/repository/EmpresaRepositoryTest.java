package com.example.demo.repository;

import com.example.demo.model.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@SpringBootTest //Levanta todo el servicio para el test de implementaion
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Para hacer test con db original en container
//@Rollback //Revierte los cambios hecho en la bd
class EmpresaRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EmpresaRepository repositoryEmpresa;

    /*@BeforeEach
    void setup(){
    }
    */

    private Empresa insertDemoEmpresa(Empresa empresa) {
        entityManager.persist(empresa);
        entityManager.flush();
        return empresa;
    }
    @Test
    void findAllWithDemo(){
        Empresa empresa1 = new Empresa("Amazon", "Cloud", "Gigante", "S.A", "USA", null);
        empresa1 = insertDemoEmpresa(empresa1);
        assertEquals(3, empresa1.getIdEmpresa());
        List<Empresa> empresas = repositoryEmpresa.findAll();
        assertEquals(3, empresas.size());
    }
    @Test
    @Sql("InsertsTests.sql")
    void findAllWithSql(){
        List<Empresa> empresas = repositoryEmpresa.findAll();
        assertEquals(4, empresas.size());
        assertEquals(1, empresas.get(0).getIdEmpresa());
    }


    @Test
    void findTopByOrderByIdEmpresaDesc() {
        Empresa nuevaEmpresa = new Empresa("Amazon", "Cloud", "Gigante", "S.A", "USA", null);
        insertDemoEmpresa(nuevaEmpresa);
        Empresa empresa = repositoryEmpresa.findTopByOrderByIdEmpresaDesc();
        assertEquals(nuevaEmpresa.getNombre(), empresa.getNombre());
    }

    @Test
    @SqlGroup(value = {
            @Sql("InsertsTests.sql")
    })
    void findAllByTamaño() {
        List<Empresa> empresas = repositoryEmpresa.findAllByTamaño("Mediana");
        assertEquals("Facebook", empresas.get(0).getNombre());
    }
}