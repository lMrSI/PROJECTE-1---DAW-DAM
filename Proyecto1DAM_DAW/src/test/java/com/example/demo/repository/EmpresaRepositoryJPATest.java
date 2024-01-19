package com.example.demo.repository;

import com.example.demo.model.Empresa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


/*@DataJpaTest
class EmpresaRepositoryJPATest {
    @Autowired private TestEntityManager entityManager;
    @Autowired private EmpresaRepository repositoryEmpresa; //SUT

    private Empresa createEmpresaDemo(Empresa empresa) {
        entityManager.persist(empresa);
        entityManager.flush();
        return empresa;
    }


    @Test //TEST 1: getter i setters
    void TestGettersSetters() {
        Empresa empresa = new
                Empresa(
                "Amazon",
                "Cloud",
                "Gigante",
                "S.A",
                "USA",
                null);
        Empresa empresaCreada = createEmpresaDemo(empresa);
        assertEquals(8, empresaCreada.getIdEmpresa());
    }
    @Test //TEST 2: findAll
    void TestFindAllWithDemo(){
        List<Empresa> empresas = repositoryEmpresa.findAll();
        assertEquals(2, empresas.size());
    }

    @Test
    @Sql("InsertsTests.sql") //Sentencias SQL en archivo
    void TestFindAllWithSql(){
        List<Empresa> empresas = repositoryEmpresa.findAll();
        assertEquals(4, empresas.size());
        assertEquals(1, empresas.get(0).getIdEmpresa());
    }


    @Test
    void TestFindTopByOrderByIdEmpresaDesc() {
        Empresa nuevaEmpresa = new Empresa("Amazon", "Cloud", "Gigante", "S.A", "USA", null);
        createEmpresaDemo(nuevaEmpresa);
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
}*/