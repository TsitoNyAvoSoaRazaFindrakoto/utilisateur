insert into role(role) values   ('Membre simple'),
                                ('Admin');

insert into tentative_connection(nombre) values(0);

-- itu16 io mot de passe io
insert into utilisateur(pseudo,email,password,id_tentative_connection,id_pin,id_token,id_role) values('Fifah','valeafifaliana@gmail.com','$2a$10$UXHGTlWb27E1kXYQMsNzyOc1Fb5fzI3w31d2Sm5aC9b0JGSov12PC',1,null,null,1);

