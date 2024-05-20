drop database if exists DBKinalExpessIN5BM;

create database DBKinalExpessIN5BM;

use DBKinalExpessIN5BM;

set global time_zone='-6:00';



create table Clientes(
	clienteID int auto_increment,
    nombreClientes varchar (50),
    apellidosClientes varchar (50),
    direccionClientes varchar (50),
    NIT varchar (10),
    telefonoClientes varchar (8),
    correoClientes varchar (45),
    primary key PK_ClienteID(clienteID)
);

create table TipoProducto(
	codigoTipoProducto int auto_increment,
    descripcionProducto varchar (100),
    primary key PK_TipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int auto_increment,
    fechaDocumento date,
    descripcion varchar(100),
    totalDocumento decimal(10,2),
    primary key PK_NumeroDocumento(numeroDocumento)
);

create table CargoEmpleado (
	codigoCargoEmpleado int auto_increment,
    nombreCargo varchar (45),
    descripcionCargo varchar (65),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table Proveedores(
	codigoProveedor int auto_increment,
    NITProveedor varchar (60),
    nombresProveedor varchar (60),
    apellidosProveedor varchar (60),
    direccionProveedor varchar (150),
    rasonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor (codigoProveedor)

);

create table Productos(
	codigoProducto varchar(15),
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
	codigoTipoProducto int,
	codigoProveedor int,
	primary key  PK_codigoProducto (codigoProducto),
	foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);

create table DetalleCompra(
	codigoDetalleCompra int auto_increment,
	costoUnitario decimal(10,2),
	cantidad int,
	codigoProducto varchar(15),
	numeroDocumento int,
	primary key PK_codigoDetalleCompra (codigoDetalleCompra),
	foreign key (codigoProducto) REFERENCES Productos(codigoProducto),
	foreign key (numeroDocumento) REFERENCES Compras(numeroDocumento)
    
);


create table Empleados(
	codigoEmpleado int auto_increment,
	nombresEmpleado varchar(50),
	apellidosEmpleado varchar(50),
	sueldo decimal(10,2),
	direccion varchar(150),
	turno varchar(15),
	codigoCargoEmpleado int,
	primary key PK_codigoEmpleado (codigoEmpleado),
	foreign key (codigoCargoEmpleado) REFERENCES CargoEmpleado(codigoCargoEmpleado)

);


create table Factura(
	numeroDeFactura int auto_increment,
	estado varchar(50),
	totalFactura decimal(10,2),
	fechaFactura varchar(45),
	clienteID int,
	codigoEmpleado int,
	primary key PK_numeroDeFactura (numeroDeFactura),
	foreign key (clienteID) REFERENCES Clientes(clienteID),
	foreign key (codigoEmpleado) REFERENCES Empleados(codigoEmpleado)

);

CREATE TABLE DetalleFactura(
	codigoDetalleFactura int auto_increment,
	precioUnitario decimal(10,2),
	cantidad int,
	numeroDeFactura int,
	codigoProducto varchar(15),
	primary key PK_codigoDetalleFactura (codigoDetalleFactura),
	foreign key (numeroDeFactura) REFERENCES Factura(numeroDeFactura),
	foreign key (codigoProducto) REFERENCES Productos(codigoProducto)
);



-- ------------------------------------------------------------------------------- Clientes --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarClientes (in ombre varchar (50), in pellidos varchar(50), in ireccionClientes varchar(50), in it varchar(10), in elefono varchar (8), in orreo varchar(45))
begin 
	insert into Clientes (Clientes.nombreClientes, Clientes.apellidosClientes, Clientes.direccionClientes, Clientes.NIT, Clientes.telefonoClientes, Clientes.correoClientes)
		values (ombre, pellidos, ireccionClientes, it, elefono,orreo);
end $$        
delimiter ;
call sp_AgregarClientes('María López', 'González', 'zona 15', '9876543210', '87654321', 'maria@gmail.com');
call sp_AgregarClientes('Juan Pérez', 'Martínez', 'zona 7', '4567890123', '23456789', 'juan@gmail.com');
call sp_AgregarClientes('Ana García', 'Fernández', 'zona 10', '8765432109', '34567890', 'ana@gmail.com');
call sp_AgregarClientes('Pedro Sánchez', 'Rodríguez', 'zona 3', '7654321098', '45678901', 'pedro@gmail.com');
call sp_AgregarClientes('Laura Martínez', 'Gómez', 'zona 17', '6543210987', '56789012', 'laura@gmail.com');
call sp_AgregarClientes('Carlos Rodríguez', 'Pérez', 'zona 12', '5432109876', '67890123', 'carlos@gmail.com');
call sp_AgregarClientes('Sofía Fernández', 'López', 'zona 5', '4321098765', '78901234', 'sofia@gmail.com');
call sp_AgregarClientes('David García', 'Hernández', 'zona 8', '3210987654', '89012345', 'david@gmail.com');
call sp_AgregarClientes('Fernanda Martínez', 'Díaz', 'zona 14', '2109876543', '90123456', 'fernanda@gmail.com');
call sp_AgregarClientes('Roberto López', 'Sánchez', 'zona 6', '1098765432', '01234567', 'roberto@gmail.com');
call sp_AgregarClientes('Patricia Pérez', 'Gómez', 'zona 19', '9876543210', '89012345', 'patricia@gmail.com');
call sp_AgregarClientes('Jorge González', 'Fernández', 'zona 11', '8765432109', '90123456', 'jorge@gmail.com');
call sp_AgregarClientes('Isabel Rodríguez', 'Martínez', 'zona 4', '7654321098', '01234567', 'isabel@gmail.com');
call sp_AgregarClientes('Santiago Hernández', 'Pérez', 'zona 22', '6543210987', '90123456', 'santiago@gmail.com');
call sp_AgregarClientes('Alejandro Pérez', 'López', 'zona 2', '4398765432', '78901234', 'alejandro@gmail.com');
call sp_AgregarClientes('Marcela Rodríguez', 'González', 'zona 18', '3987654321', '89012345', 'marcela@gmail.com');
call sp_AgregarClientes('Ricardo Martínez', 'Sánchez', 'zona 9', '2109543210', '90123456', 'ricardo@gmail.com');
-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarClientes(in ienteID varchar(10))
begin
    delete from Clientes
    where Clientes.clienteID = ienteID;
end$$
delimiter ;
call sp_EliminarClientes ('12');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarClientes(in ienteID  int,in ombre varchar (50), in pellidos varchar(50), in ireccionClientes varchar(50), in it varchar(10), in elefono varchar (8), in orreo varchar(45))
begin
    update Clientes
    set 
        Clientes.nombreClientes= ombre,
        Clientes.apellidosClientes =  pellidos ,
        Clientes.direccionClientes = ireccionClientes,
        Clientes.NIT = it,
        Clientes.telefonoClientes = elefono,
        Clientes.correoClientes = orreo
        where
        Clientes.clienteID = ienteID;
end$$
delimiter ;

call sp_ActualizarClientes(1,'Daniela García', 'Martínez', 'zona 16', '5432109543', '67890123', 'daniela@gmail.com');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarClientes ()
begin 
	select
    c.clienteID,
    c.nombreClientes,
    c.apellidosClientes,
    c.direccionClientes,
    c.NIT,
    c.telefonoClientes,
    c.correoClientes
    from clientes c;
end $$        
delimiter ;
call sp_MostrarClientes;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarClientes(in ienteID int)
begin 
  select *from Clientes where Clientes.clienteID=ienteID;
end$$ 
delimiter ;
 call sp_BuscarClientes(1);









-- ------------------------------------------------------------------------------- TipoProducto --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarTipoProducto (in cripcion varchar (100))
begin 
	insert into TipoProducto (TipoProducto.descripcionProducto)
		values (cripcion);
end $$        
delimiter ;
call sp_AgregarTipoProducto('Bicicleta Plegable UrbanRide: Compacta, ligera, perfecta para la ciudad.');
call sp_AgregarTipoProducto('Auriculares Inalámbricos AirBeat: Sonido claro, conexión estable, diseño ergonómico.');
call sp_AgregarTipoProducto('Cafetera Expresso MiniBar: Café delicioso, tamaño compacto, fácil de usar.');
call sp_AgregarTipoProducto('Altavoz Portátil BeatBox: Sonido potente, resistente al agua, ideal para exteriores.');
call sp_AgregarTipoProducto('Tablet Multifunción TechTab: Pantalla HD, rendimiento rápido, versátil.');
call sp_AgregarTipoProducto('Mochila UrbanX 300: Espaciosa, duradera, estilo moderno.');
call sp_AgregarTipoProducto('Reloj Deportivo FitTrack: Seguimiento de actividad, resistente al agua, diseño deportivo.');
call sp_AgregarTipoProducto('Cámara Compacta SnapShot: Fotos nítidas, fácil de llevar, perfecta para viajes.');
call sp_AgregarTipoProducto('Secador de Pelo TurboDry: Secado rápido, ajustes de temperatura, portátil.');
call sp_AgregarTipoProducto('Maletín Ejecutivo ElitePro: Elegante, organizado, ideal para negocios.');
call sp_AgregarTipoProducto('Botella Térmica EcoTherm: Mantiene las bebidas frías o calientes, diseño elegante, duradera.');
call sp_AgregarTipoProducto('Zapatillas Running SpeedFlex: Amortiguación, ligereza, máximo confort.');
call sp_AgregarTipoProducto('Silla Oficina ErgoPro: Ergonómica, ajustable, comodidad garantizada.');
call sp_AgregarTipoProducto('Teclado Gamer TurboKeys: Respuesta rápida, retroiluminación LED, diseño gamer.');
call sp_AgregarTipoProducto('Parrilla Eléctrica BBQMaster: Grill sin humo, fácil de limpiar, perfecta para interiores.');
-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarTipoProducto(in digoTipoProducto int)
begin
    delete from TipoProducto
    where TipoProducto.codigoTipoProducto = digoTipoProducto;
end$$
delimiter ;
call sp_EliminarTipoProducto (12);
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarTipoProducto(in digoTipoProducto int,in cripcion varchar (100))
begin
    update TipoProducto
    set 
        TipoProducto.descripcionProducto= cripcion
        where
        TipoProducto.codigoTipoProducto = digoTipoProducto;
end$$
delimiter ;

call sp_ActualizarTipoProducto(1,'Robot Aspirador CleanBot: Limpieza automática, navegación inteligente, eficiente.');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarTipoProducto ()
begin 
	select
    t.codigoTipoProducto,
    t.descripcionProducto
    from TipoProducto t;
end $$        
delimiter ;
call sp_MostrarTipoProducto;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarTipoProducto(in digoTipoProducto int)
begin 
  select *from TipoProducto where TipoProducto.codigoTipoProducto=digoTipoProducto;
end$$ 
delimiter ;
call sp_BuscarTipoProducto(1);









-- ------------------------------------------------------------------------------- Proveedores --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarProveedores (in ITProveedor varchar (60),in ombresProveedor varchar (60),in pellidosProveedor varchar (60),in ireccionProveedor varchar (150),in asonSocial varchar (60),in ontactoPrincipal varchar (100),in aginaWeb varchar (50))
begin
	insert into Proveedores ( Proveedores.NITProveedor, Proveedores.nombresProveedor, Proveedores.apellidosProveedor, Proveedores.direccionProveedor, Proveedores.rasonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb)
		values (ITProveedor,ombresProveedor,pellidosProveedor,ireccionProveedor,asonSocial,ontactoPrincipal,aginaWeb);
end $$        
delimiter ;
call sp_AgregarProveedores('5435455345345', 'González', 'Luis', 'zona 22', 'Pepsico  Servicios , S.L','43534534','https://www.google.com');
call sp_AgregarProveedores('3323213123323', 'Martínez', 'Emanue', 'zona 23', 'Servicios Centrales, S.L', '65646546','https://www.google.com');
CALL sp_AgregarProveedores('1234567890123', 'García', 'Ana', 'zona 24', 'García Hermanos, S.A.','78901234','https://www.google.com');
CALL sp_AgregarProveedores('9876543210987', 'López', 'Juan', 'zona 25', 'López & Asociados, S.L.', '56789012','https://www.google.com');
CALL sp_AgregarProveedores('1112223334445', 'Hernández', 'María', 'zona 26', 'Hernández y Cía, S.C.P.', '34567890','https://www.google.com');
CALL sp_AgregarProveedores('5554443332221', 'Pérez', 'Pedro', 'zona 27', 'Pérez Servicios Integrales, S.A.', '12345678','https://www.google.com');
CALL sp_AgregarProveedores('6667778889990', 'Rodríguez', 'David', 'zona 28', 'Rodríguez Soluciones Empresariales, S.L.','90123456','https://www.google.com');
CALL sp_AgregarProveedores('4445556667772', 'Sánchez', 'Laura', 'zona 29', 'Sánchez Consultoría y Asesoría, S.A.', '67890123','https://www.google.com');
CALL sp_AgregarProveedores('1239874563210', 'Gómez', 'Carlos', 'zona 30', 'Gómez Innovaciones Tecnológicas, S.A.', '45678901','https://www.google.com');
CALL sp_AgregarProveedores('9998887776663', 'Fernández', 'Sofía', 'zona 31', 'Fernández Soluciones Digitales, S.L.', '23456789','https://www.google.com');
CALL sp_AgregarProveedores('6543210987654', 'Martín', 'Diego', 'zona 32', 'Martín Consultoría Empresarial, S.A.','01234567','https://www.google.com');
CALL sp_AgregarProveedores('1110002223337', 'Moreno', 'Lucía', 'zona 33', 'Moreno Servicios Informáticos, S.L.', '89012345','https://www.google.com');

-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarProveedores(in odigoProveedor int)
begin
    delete from Proveedores
    where Proveedores.codigoProveedor = odigoProveedor;
end$$
delimiter ;
call sp_EliminarProveedores ('12');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarProveedores(in odigoProveedor int,in ITProveedor varchar (60),in ombresProveedor varchar (60),in pellidosProveedor varchar (60),in ireccionProveedor varchar (150),in asonSocial varchar (60),in ontactoPrincipal varchar (100),in aginaWeb varchar (50))
begin
    update Proveedores
    set 
        Proveedores.NITProveedor =  ITProveedor ,
        Proveedores.nombresProveedor = ombresProveedor,
        Proveedores.apellidosProveedor = pellidosProveedor,
        Proveedores.direccionProveedor = ireccionProveedor,
        Proveedores.rasonSocial = asonSocial,
        Proveedores.contactoPrincipal = ontactoPrincipal,
        Proveedores.paginaWeb = aginaWeb
        
        where
        Proveedores.codigoProveedor = odigoProveedor;
end$$
delimiter ;

CALL sp_ActualizarProveedores(1,'8889990001112', 'Torres', 'Miguel', 'zona 35', 'Torres Servicios de Mantenimiento, S.L.', '45678901','https://www.google.com');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarProveedores ()
begin 
	select
	p.codigoProveedor ,
	p.NITProveedor  ,
	p.nombresProveedor ,
	p.apellidosProveedor ,
	p.direccionProveedor,
	p.rasonSocial,
	p.contactoPrincipal,
	p.paginaWeb 
    from proveedores p;
end $$        
delimiter ;
call sp_MostrarProveedores;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarProveedores(in odigoProveedor int)
begin 
  select *from Proveedores where Proveedores.codigoProveedor = odigoProveedor;
end$$ 
delimiter ;
call sp_BuscarProveedores(1);






-- ------------------------------------------------------------------------------- compras --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarCompras (in echaDocumento date,in escripcion varchar(100),in otalDocumento decimal(10,2))
begin
	insert into Compras ( Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento)
		values (echaDocumento,escripcion,otalDocumento);
end $$        
delimiter ;
call sp_AgregarCompras('2020-06-12','2 Camisetas de algodón, 1 Pantalón de mezclilla, 1 Par de zapatos deportivos, 1 Mochila resistente.',199.88);
call sp_AgregarCompras('2021-07-17','1 Conjunto de pijama de franela, 1 Bata de baño suave, 1 Par de pantuflas cómodas.',99.99);
call sp_AgregarCompras('2021-06-30','2 Blusas de moda, 1 Falda plisada, 1 Collar de perlas.',149.50);
call sp_AgregarCompras('2021-04-18','1 Camiseta de algodón orgánico, 1 Pantalón de yoga, 1 Tapete de yoga antideslizante.',79.99);
call sp_AgregarCompras('2021-02-13','1 Vestido de noche elegante, 1 Bolso de fiesta, 1 Par de tacones altos.',199.99);
call sp_AgregarCompras('2020-12-09','1 Set de cocina de acero inoxidable, 1 Juego de cuchillos profesionales, 1 Tabla de cortar de bambú.',129.95);
call sp_AgregarCompras('2020-06-12', '2 Camisetas de algodón, 1 Pantalón de mezclilla, 1 Par de zapatos deportivos, 1 Mochila resistente.', 199.88);
call sp_AgregarCompras('2021-07-17', '1 Conjunto de pijama de franela, 1 Bata de baño suave, 1 Par de pantuflas cómodas.', 99.99);
call sp_AgregarCompras('2021-06-30', '2 Blusas de moda, 1 Falda plisada, 1 Collar de perlas.', 149.50);
call sp_AgregarCompras('2021-04-18', '1 Camiseta de algodón orgánico, 1 Pantalón de yoga, 1 Tapete de yoga antideslizante.', 79.99);
call sp_AgregarCompras('2021-02-13', '1 Vestido de noche elegante, 1 Bolso de fiesta, 1 Par de tacones altos.', 199.99);
call sp_AgregarCompras('2020-12-09', '1 Set de cocina de acero inoxidable, 1 Juego de cuchillos profesionales, 1 Tabla de cortar de bambú.', 129.95);


-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarCompras(in umeroDocumento int)
begin
    delete from Compras
    where Compras.numeroDocumento = umeroDocumento;
end$$
delimiter ;
call sp_EliminarCompras ('12');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarCompras(in umeroDocumento int,in echaDocumento date,in escripcion varchar(100),in otalDocumento decimal(10,2))
begin
    update Compras
    set 
        Compras.fechaDocumento =  echaDocumento ,
        Compras.descripcion = escripcion,
        Compras.totalDocumento = otalDocumento
        
        where
        Compras.numeroDocumento = umeroDocumento;
end$$
delimiter ;

call sp_ActualizarCompras(1,'2020-06-12','Mima a tu mascota con una variedad de alimentos y juguetes en PetParadise',100.45);
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarCompras ()
begin 
	select
	o.numeroDocumento ,
	o.fechaDocumento  ,
	o.descripcion ,
	o.totalDocumento 
    from compras o;
end $$        
delimiter ;
call sp_MostrarCompras;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarCompras(in umeroDocumento int)
begin 
  select *from Compras where Compras.numeroDocumento = umeroDocumento;
end$$ 
delimiter ;
call sp_BuscarCompras(1);







-- ------------------------------------------------------------------------------- Empleado --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarCargoEmpleado (in ombreCargo varchar(45),in escripcionCargo varchar (65))
begin
	insert into CargoEmpleado ( CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo)
		values (ombreCargo,escripcionCargo);
end $$        
delimiter ;

CALL sp_AgregarCargoEmpleado('Gerente de Ventas', 'Lidera equipo, establece objetivos, incrementa ventas.');
CALL sp_AgregarCargoEmpleado('Analista de Marketing', 'Investiga mercado, desarrolla estrategias, analiza datos.');
CALL sp_AgregarCargoEmpleado('Ingeniero de Software', 'Diseña, desarrolla, prueba software.');
CALL sp_AgregarCargoEmpleado('Asistente Administrativo', 'Brinda apoyo administrativo.');
CALL sp_AgregarCargoEmpleado('Especialista en Recursos Humanos', 'Gestiona personal, administra beneficios, resuelve conflictos.');
CALL sp_AgregarCargoEmpleado('Contador Financiero', 'Gestiona finanzas, prepara informes, asesora financiero.');
CALL sp_AgregarCargoEmpleado('Director de Operaciones', 'Supervisa operaciones, optimiza procesos, mejora eficiencia.');
CALL sp_AgregarCargoEmpleado('Desarrollador Web', 'Crea y mantiene sitios web, optimiza rendimiento.');
CALL sp_AgregarCargoEmpleado('Gerente de Proyecto', 'Planifica, ejecuta, cierra proyectos.');
CALL sp_AgregarCargoEmpleado('Especialista en Atención al Cliente', 'Resuelve , mejora satisfacción, gestiona quejas.');
CALL sp_AgregarCargoEmpleado('Consultor de Negocios', 'Asesora empresas, analiza procesos, recomienda mejoras.');
CALL sp_AgregarCargoEmpleado('Diseñador Gráfico', 'Crea contenido visual, diseña materiales , identidades de marca.');
-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarCargoEmpleado(in odigoCargoEmpleado int)
begin
    delete from CargoEmpleado
    where CargoEmpleado.codigoCargoEmpleado = odigoCargoEmpleado;
end$$
delimiter ;
call sp_EliminarCargoEmpleado ('12');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarCargoEmpleado(in umeroDocumento int,in ombreCargo varchar(45),in escripcionCargo varchar (65))
begin
    update CargoEmpleado
    set 
        CargoEmpleado.nombreCargo = ombreCargo,
        CargoEmpleado.descripcionCargo = escripcionCargo
        
        where
        CargoEmpleado.codigoCargoEmpleado = umeroDocumento;
end$$
delimiter ;

call sp_ActualizarCargoEmpleado(1,'Coordinador de Eventos','Planifica, coordina eventos, gestiona logística.');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarCargoEmpleado ()
begin 
	select
	e.codigoCargoEmpleado ,
	e.nombreCargo  ,
	e.descripcionCargo 
    from cargoEmpleado e;
end $$        
delimiter ;
call sp_MostrarCargoEmpleado;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarCargoEmpleado(in odigoCargoEmpleado int)
begin 
  select *from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = odigoCargoEmpleado;
end$$ 
delimiter ;
call sp_BuscarCargoEmpleado(1);



-- ------------------------------------------------------------------------------- Producto --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

DELIMITER $$

create procedure sp_agregarProducto( in odigoProducto varchar(15),in escripcionProducto varchar(15),in recioUnitario decimal(10,2),in recioDocena decimal(10,2),in recioMayor decimal(10,2),in xistencia int,in odigoTipoProducto int,in odigoProveedor int)
begin
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(odigoProducto, escripcionProducto, recioUnitario, recioDocena, recioMayor, xistencia, odigoTipoProducto, odigoProveedor);
end$$
DELIMITER ;

CALL sp_agregarProducto('P001', 'Arroz', 5.99, 68.99, 129.99, 100, 1, 1);
CALL sp_agregarProducto('P002', 'Frijoles', 3.49, 39.99, 74.99, 150, 2, 2);
CALL sp_agregarProducto('P003', 'Aceite', 8.99, 102.99, 194.99,  80, 3, 3);
CALL sp_agregarProducto('P004', 'Leche Entera', 2.99, 32.99, 62.99, 120, 4, 4);
CALL sp_agregarProducto('P005', 'Azúcar', 4.49, 51.99, 98.99, 90, 5, 5);
CALL sp_agregarProducto('P006', 'Harina', 3.99, 45.99, 89.99, 80, 6, 6);
CALL sp_agregarProducto('P007', 'Leche', 1.99, 20.99, 39.99, 100, 7, 7);
CALL sp_agregarProducto('P008', 'Arroz', 2.99, 35.99, 67.99, 120, 8, 8);
CALL sp_agregarProducto('P009', 'Frijoles', 3.49, 40.99, 78.99, 110, 9, 9);
CALL sp_agregarProducto('P010', 'Aceite', 4.99, 55.99, 108.99, 60, 10, 10);
CALL sp_agregarProducto('P011', 'Sal', 0.99, 10.99, 19.99, 200, 2, 3);



-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Delimiter $$
create procedure sp_mostrarProductos()
	begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
	end$$
Delimiter ;

call sp_mostrarProductos();

-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

DELIMITER $$
create procedure sp_actualizarProducto(in odigoProducto varchar(15),in uevaDescripcionProducto varchar(15),in uevoPrecioUnitario decimal(10,2),in uevoPrecioDocena decimal(10,2),in uevoPrecioMayor decimal(10,2),in uevaExistencia int,in uevoCodigoTipoProducto int,in uevoCodigoProveedor int)
begin
    update Productos
    set descripcionProducto = uevaDescripcionProducto,
        precioUnitario = uevoPrecioUnitario,
        precioDocena = uevoPrecioDocena,
        precioMayor = uevoPrecioMayor,
        existencia = uevaExistencia,
        codigoTipoProducto = uevoCodigoTipoProducto,
        codigoProveedor = uevoCodigoProveedor
    where codigoProducto = odigoProducto;
end$$
DELIMITER ;

call sp_actualizarProducto('P001', 'Pollo', 8.99, 69.99, 130.99, 100, 2, 2);

-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Delimiter $$
create procedure sp_eliminarProducto(in _codigoProducto varchar(15))
begin
    delete from Productos
    where codigoProducto = _codigoProducto;
end $$

DELIMITER ;

call sp_eliminarProducto('P011');






-- ------------------------------------------------------------------------------- DetalleCompra --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_AgregarDetalleCompra(IN p_costoUnitario DECIMAL(10,2),IN p_cantidad INT,IN p_codigoProducto VARCHAR(15),IN p_numeroDocumento INT)
BEGIN
    INSERT INTO DetalleCompra( DetalleCompra.costoUnitario, DetalleCompra.cantidad, DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento)
    VALUES( p_costoUnitario, p_cantidad, p_codigoProducto, p_numeroDocumento);
END$$
delimiter ;
CALL sp_AgregarDetalleCompra(23.45, 34, 'P001', 1);
CALL sp_AgregarDetalleCompra(17.89, 22, 'P002', 2);
CALL sp_AgregarDetalleCompra(10.67, 45, 'P003', 3);
CALL sp_AgregarDetalleCompra(35.60, 18, 'P004', 4);
CALL sp_AgregarDetalleCompra(28.75, 39, 'P005', 5);
CALL sp_AgregarDetalleCompra(19.99, 27, 'P006', 6);
CALL sp_AgregarDetalleCompra(42.30, 31, 'P007', 7);
CALL sp_AgregarDetalleCompra(15.50, 14, 'P008', 8);
CALL sp_AgregarDetalleCompra(29.75, 26, 'P009', 9);
CALL sp_AgregarDetalleCompra(38.20, 19, 'P010', 10);



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarDetalleCompra(in odigoDetalleCompra int)
begin
    delete from DetalleCompra
    where DetalleCompra.codigoDetalleCompra = odigoDetalleCompra;
end$$
delimiter ;
call sp_EliminarCargoEmpleado ('10');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarDetalleCompra(in odigoDetalleCompra int,IN p_costoUnitario DECIMAL(10,2),IN p_cantidad INT,IN p_codigoProducto VARCHAR(15),IN p_numeroDocumento INT)
begin
    update DetalleCompra
    set 
        DetalleCompra.costoUnitario = p_costoUnitario,
        DetalleCompra.cantidad = p_cantidad,
        DetalleCompra.codigoProducto = p_codigoProducto,
        DetalleCompra.numeroDocumento = p_numeroDocumento

        
        where
        DetalleCompra.codigoDetalleCompra = odigoDetalleCompra;
end$$
delimiter ;

call sp_ActualizarDetalleCompra(1,23.45, 34, 'P002', 2);
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarDetalleCompra ()
begin 
	select
	d.codigoDetalleCompra,
	d.costoUnitario,
	d.cantidad, 
	d.codigoProducto,
	d.numeroDocumento 
    from detalleCompra d;
end $$        
delimiter ;
call sp_MostrarDetalleCompra;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarDetalleCompra(in odigoDetalleCompra int)
begin 
  select *from DetalleCompra where DetalleCompra.codigoDetalleCompra = odigoDetalleCompra;
end$$ 
delimiter ;
call sp_BuscarDetalleCompra(1);






-- ------------------------------------------------------------------------------- Empleado --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_AgregarEmpleado(IN p_nombresEmpleado VARCHAR(50),IN p_apellidosEmpleado VARCHAR(50),IN p_sueldo DECIMAL(10,2),IN p_direccion VARCHAR(150),IN p_turno VARCHAR(15),IN p_codigoCargoEmpleado INT)
BEGIN
    INSERT INTO Empleados(nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    VALUES( p_nombresEmpleado, p_apellidosEmpleado, p_sueldo, p_direccion, p_turno, p_codigoCargoEmpleado);
END$$
delimiter ;

CALL sp_AgregarEmpleado('Jose Emanuel', 'López Laynes', 234.56, 'zona 12', 'Primer turno', 1);
CALL sp_AgregarEmpleado('Ana Maria', 'Gomez Perez', 190.75, 'zona 4', 'Segundo turno', 2);
CALL sp_AgregarEmpleado('Carlos Alberto', 'Ramirez Torres', 210.30, 'zona 8', 'Tercer turno', 3);
CALL sp_AgregarEmpleado('Luisa Fernanda', 'Martinez Diaz', 180.50, 'zona 10', 'Primer turno', 4);
CALL sp_AgregarEmpleado('Pedro Juan', 'Mendez Ruiz', 200.80, 'zona 5', 'Segundo turno', 5);
CALL sp_AgregarEmpleado('Maria Isabel', 'Hernandez Lopez', 220.60, 'zona 9', 'Tercer turno', 6);
CALL sp_AgregarEmpleado('Juan Carlos', 'Vargas Morales', 195.40, 'zona 3', 'Primer turno', 7);
CALL sp_AgregarEmpleado('Elena Patricia', 'Fernandez Jimenez', 230.25, 'zona 7', 'Segundo turno', 8);
CALL sp_AgregarEmpleado('Miguel Angel', 'Castro Garcia', 175.90, 'zona 6', 'Tercer turno', 9);



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarEmpleados(in odigoEmpleado int)
begin
    delete from Empleados
    where Empleados.codigoEmpleado = odigoEmpleado;
end$$
delimiter ;
call sp_EliminarEmpleados ('10');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarEmpleados(IN odigoEmpleado INT,IN p_nuevosNombresEmpleado VARCHAR(50),IN p_nuevosApellidosEmpleado VARCHAR(50),IN p_nuevoSueldo DECIMAL(10,2),IN p_nuevaDireccion VARCHAR(150),IN p_nuevoTurno VARCHAR(15),IN p_nuevoCodigoCargoEmpleado INT
)
begin
    update Empleados
    set Empleados.nombresEmpleado = p_nuevosNombresEmpleado,
        Empleados.apellidosEmpleado = p_nuevosApellidosEmpleado,
        Empleados.sueldo = p_nuevoSueldo,
        Empleados.direccion = p_nuevaDireccion,
        Empleados.turno = p_nuevoTurno,
        Empleados.codigoCargoEmpleado = p_nuevoCodigoCargoEmpleado
    WHERE Empleados.codigoEmpleado = odigoEmpleado;
end$$
delimiter ;

call sp_ActualizarEmpleados(1,'Ana Maria', 'Gomez Perez', 190.75, 'zona 4', 'Segundo turno', 2);
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarEmpleados ()
begin 
	select
	e.codigoEmpleado,
	e.nombresEmpleado,
	e.apellidosEmpleado, 
	e.sueldo,
	e.direccion,
	e.turno,
	e.codigoCargoEmpleado
    from empleados e;
end $$        
delimiter ;
call sp_MostrarEmpleados;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarEmpleados(in odigoEmpleado int)
begin 
  select *from Empleados where Empleados.codigoEmpleado = odigoEmpleado;
end$$ 
delimiter ;
call sp_BuscarEmpleados(1);







-- ------------------------------------------------------------------------------- Facctura  --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_AgregarFactura(IN p_estado VARCHAR(50),IN p_totalFactura DECIMAL(10,2),IN p_fechaFactura VARCHAR(45),IN p_codigoCliente INT,IN p_codigoEmpleado INT)
BEGIN
    INSERT INTO Factura(estado, totalFactura, fechaFactura, clienteID, codigoEmpleado)
    VALUES( p_estado, p_totalFactura, p_fechaFactura, p_codigoCliente, p_codigoEmpleado);
END$$
delimiter ;

CALL sp_AgregarFactura('Pendiente', 4125.00, '2024-05-10', 1, 1);
CALL sp_AgregarFactura('Pagada', 2500.50, '2024-04-15', 2, 2);
CALL sp_AgregarFactura('Pendiente', 3750.75, '2024-05-05', 3, 3);
CALL sp_AgregarFactura('Parcialmente Pagada', 1800.00, '2024-05-12', 4, 4);
CALL sp_AgregarFactura('Cancelada', 3200.00, '2024-05-01', 5, 5);
CALL sp_AgregarFactura('Pendiente', 4150.25, '2024-05-20', 6, 6);
CALL sp_AgregarFactura('Pagada', 5000.00, '2024-05-08', 7, 7);
CALL sp_AgregarFactura('Pendiente', 2750.40, '2024-05-15', 8, 8);
CALL sp_AgregarFactura('Parcialmente Pagada', 2100.00, '2024-04-28', 9, 9);



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarFactura(in umeroDeFactura int)
begin
    delete from Factura
    where Factura.numeroDeFactura = umeroDeFactura;
end$$
delimiter ;
call sp_EliminarFactura ('10');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarFactura (IN umeroDeFactura INT,IN uevoEstado VARCHAR(50),IN uevoTotalFactura DECIMAL(10,2),IN uevaFechaFactura VARCHAR(45),IN uevoCodigoCliente INT,IN uevoCodigoEmpleado INT)
begin
    update Factura
    SET Factura.estado = uevoEstado,
        Factura.totalFactura = uevoTotalFactura,
        Factura.fechaFactura = uevaFechaFactura,
        Factura.clienteID = uevoCodigoCliente,
        Factura.codigoEmpleado = uevoCodigoEmpleado
    WHERE Factura.numeroDeFactura = umeroDeFactura;
end$$
delimiter ;

call sp_ActualizarFactura(1,'Parcialmente Pagada ', 234.56, '2020-12-09', 1, 1);
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarFactura ()
begin 
	select
	f.numeroDeFactura,
	f.estado,
	f.totalFactura, 
	f.fechaFactura,
	f.clienteID,
	f.codigoEmpleado
    from factura f;
end $$        
delimiter ;
call sp_MostrarFactura;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarFactura(in umeroDeFactura int)
begin 
  select *from Factura where Factura.numeroDeFactura = umeroDeFactura;
end$$ 
delimiter ;
call sp_BuscarFactura(1);








-- ------------------------------------------------------------------------------- DetalleFactura  --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_AgregarDetalleFactura (IN p_precioUnitario DECIMAL(10,2),IN p_cantidad INT,IN p_numeroDeFactura INT,IN p_codigoProducto VARCHAR(15)
)
BEGIN
    INSERT INTO DetalleFactura( precioUnitario, cantidad, numeroDeFactura, codigoProducto)
    VALUES( p_precioUnitario, p_cantidad, p_numeroDeFactura, p_codigoProducto);
END$$
delimiter ;

CALL sp_AgregarDetalleFactura(123.00, 234, 1, 'P001');
CALL sp_AgregarDetalleFactura(150.00, 235, 2, 'P002');
CALL sp_AgregarDetalleFactura(300.75, 236, 3, 'P003');
CALL sp_AgregarDetalleFactura(450.50, 237, 4, 'P004');
CALL sp_AgregarDetalleFactura(200.00, 238, 5, 'P005');
CALL sp_AgregarDetalleFactura(175.25, 239, 6, 'P006');
CALL sp_AgregarDetalleFactura(225.00, 240, 7, 'P007');
CALL sp_AgregarDetalleFactura(500.80, 241, 8, 'P008');
CALL sp_AgregarDetalleFactura(350.60, 242, 9, 'P009');



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarDetalleFactura(in odigoDetalleFactura int)
begin
    delete from DetalleFactura
    where DetalleFactura.codigoDetalleFactura = odigoDetalleFactura;
end$$
delimiter ;
call sp_EliminarDetalleFactura ('10');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarDetalleFactura (IN odigoDetalleFactura INT,IN p_nuevoPrecioUnitario DECIMAL(10,2),IN p_nuevaCantidad INT,IN p_nuevoNumeroDeFactura INT,IN p_nuevoCodigoProducto VARCHAR(15))
begin
    update DetalleFactura
    SET precioUnitario = p_nuevoPrecioUnitario,
        cantidad = p_nuevaCantidad,
        numeroDeFactura = p_nuevoNumeroDeFactura,
        codigoProducto = p_nuevoCodigoProducto
    WHERE codigoDetalleFactura = odigoDetalleFactura;
end$$
delimiter ;

call sp_ActualizarDetalleFactura(1,23.55, 234, 1, 'P001');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarDetalleFactura()
begin 
	select
	d.codigoDetalleFactura,
	d.precioUnitario,
	d.cantidad, 
	d.numeroDeFactura,
	d.codigoProducto
    from detalleFactura d;
end $$        
delimiter ;
call sp_MostrarDetalleFactura;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarDetalleFactura(in odigoDetalleFactura int)
begin 
  select *from DetalleFactura where DetalleFactura.codigoDetalleFactura = odigoDetalleFactura;
end$$ 
delimiter ;
call sp_BuscarDetalleFactura(1);



