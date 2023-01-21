package com.tpe.repository;

import com.tpe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email); //Burada existsBy dan sonra isim farkli yaparak farkli injectionlar yapailiriz.

    List<Customer> findByName(String name);

    //findBy methodunu and parametresiyle iki icerik alabilecek sekilde de düzenleyebiliriz.
    List<Customer> findByNameAndLastName(String name, String lastName);

    @Query("select c from Customer c where lower(c.name) like %:pName%")
    List<Customer> findAllByNameLike(@Param("pName") String name);
}
