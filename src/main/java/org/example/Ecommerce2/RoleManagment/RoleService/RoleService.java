package org.example.Ecommerce2.RoleManagment.RoleService;

import org.example.Ecommerce2.RoleManagment.Repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    final
    RoleRepository roleRepository ;


    public RoleService(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

}
