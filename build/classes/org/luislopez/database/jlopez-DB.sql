-- José Luis Emanuel López Laynes
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
	codigoProducto int auto_increment,
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	existencia int,
	codigoTipoProducto int,
	codigoProveedor int,
	primary key  PK_codigoProducto (codigoProducto),
	foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto)on delete cascade,
	foreign key (codigoProveedor) references Proveedores(codigoProveedor)on delete cascade
);

create table DetalleCompra(
	codigoDetalleCompra int auto_increment,
	costoUnitario decimal(10,2),
	cantidad int,
	codigoProducto int,
	numeroDocumento int,
	primary key PK_codigoDetalleCompra (codigoDetalleCompra),
	foreign key (codigoProducto) REFERENCES Productos(codigoProducto)on delete cascade,
	foreign key (numeroDocumento) REFERENCES Compras(numeroDocumento)on delete cascade
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
	foreign key (codigoCargoEmpleado) REFERENCES CargoEmpleado(codigoCargoEmpleado)on delete cascade
);

create table Factura(
	numeroDeFactura int auto_increment,
	estado varchar(50),
	totalFactura decimal(10,2),
	fechaFactura varchar(45),
	clienteID int,
	codigoEmpleado int,
	primary key PK_numeroDeFactura (numeroDeFactura),
	foreign key (clienteID) REFERENCES Clientes(clienteID) on delete cascade ,
	foreign key (codigoEmpleado) REFERENCES Empleados(codigoEmpleado) on delete cascade 

);

CREATE TABLE DetalleFactura(
	codigoDetalleFactura int auto_increment,
	precioUnitario decimal(10,2),
	cantidad int,
	numeroDeFactura int,
	codigoProducto int,
	primary key PK_codigoDetalleFactura (codigoDetalleFactura),
	foreign key (numeroDeFactura) REFERENCES Factura(numeroDeFactura) on delete cascade ,
	foreign key (codigoProducto) REFERENCES Productos(codigoProducto)on delete cascade 
);

create table EmailProveedor(
	codigoEmailProveedor int auto_increment,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
    foreign key (codigoProveedor) references Proveedores(codigoProveedor) on delete cascade 

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


-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarCompras(in umeroDocumento int)
begin
    delete from Compras
    where Compras.numeroDocumento = umeroDocumento;
end$$
delimiter ;
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


delimiter $$
create procedure sp_actualizarComprasTotal(in numDoc int,in total decimal(10,2))
begin
	update Compras 
	set 
		Compras.totalDocumento=total
    where
		Compras.numeroDocumento=numDoc;
end $$
delimiter ;







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
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarCompras(in umeroDocumento int)
begin 
  select *from Compras where Compras.numeroDocumento = umeroDocumento;
end$$ 
delimiter ;

-- ------------------------------------------------------------------------------- Cargo de Empleado --------------------------------------------------------------------------------------------------------
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

create procedure sp_agregarProducto(in escripcionProducto varchar(15),in recioUnitario decimal(10,2),in recioDocena decimal(10,2),in recioMayor decimal(10,2),in xistencia int,in odigoTipoProducto int,in odigoProveedor int)
begin
    INSERT INTO Productos( descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(escripcionProducto, recioUnitario, recioDocena, recioMayor, xistencia, odigoTipoProducto, odigoProveedor);
end$$
DELIMITER ;


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
create procedure sp_actualizarProducto(in odigoProducto int,in uevaDescripcionProducto varchar(15),in uevoPrecioUnitario decimal(10,2),in uevoPrecioDocena decimal(10,2),in uevoPrecioMayor decimal(10,2),in uevaExistencia int,in uevoCodigoTipoProducto int,in uevoCodigoProveedor int)
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


-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Delimiter $$
create procedure sp_eliminarProducto(in _codigoProducto int)
begin
    delete from Productos
    where codigoProducto = _codigoProducto;
end $$

DELIMITER ;

call sp_eliminarProducto('11');


-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarProductos(in odigoProducto int)
begin 
  select *from Productos where Productos.codigoProducto = odigoProducto;
end$$ 
delimiter ;
call sp_BuscarProductos(1);

delimiter $$
create procedure sp_actualizarPreciosProductos(in codProd varchar(15),in precUnit decimal(10,2),in precDoc decimal(10,5), in precMay decimal(10,2), in exist int)
begin
	update Productos 
	set 
		Productos.precioUnitario=precUnit,
		Productos.precioDocena=precDoc,
        Productos.precioMayor=precMay,
        Productos.existencia=exist
    where
		Productos.codigoProducto=codProd;
end $$
delimiter ;

-- ------------------------------------------------------------------------------- DetalleCompra --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_AgregarDetalleCompra(IN p_costoUnitario DECIMAL(10,2),IN p_cantidad INT,IN p_codigoProducto int,IN p_numeroDocumento INT)
BEGIN
    INSERT INTO DetalleCompra( DetalleCompra.costoUnitario, DetalleCompra.cantidad, DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento)
    VALUES( p_costoUnitario, p_cantidad, p_codigoProducto, p_numeroDocumento);
END$$
delimiter ;



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarDetalleCompra(in odigoDetalleCompra int)
begin
    delete from DetalleCompra
    where DetalleCompra.codigoDetalleCompra = odigoDetalleCompra;
end$$
delimiter ;
call sp_EliminarDetalleCompra ('10');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarDetalleCompra(in odigoDetalleCompra int,IN p_costoUnitario DECIMAL(10,2),IN p_cantidad INT,IN p_codigoProducto int,IN p_numeroDocumento INT)
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



-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------









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

-- ------------------------------------------------------------------------------- Factura  --------------------------------------------------------------------------------------------------------
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

delimiter $$
create procedure sp_actualizarFacturaTotal(in numFac int,in total decimal(10,2))
begin
	update Factura 
	set 
		Factura.totalFactura=total
    where
		Factura.numeroDeFactura=numFac;
end $$
delimiter ;

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

CREATE PROCEDURE sp_AgregarDetalleFactura (IN p_precioUnitario DECIMAL(10,2),IN p_cantidad INT,IN p_numeroDeFactura INT,IN p_codigoProducto int)
BEGIN
    INSERT INTO DetalleFactura( precioUnitario, cantidad, numeroDeFactura, codigoProducto)
    VALUES( p_precioUnitario, p_cantidad, p_numeroDeFactura, p_codigoProducto);
END$$
delimiter ;


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
create procedure sp_ActualizarDetalleFactura (IN odigoDetalleFactura INT,IN p_nuevoPrecioUnitario DECIMAL(10,2),IN p_nuevaCantidad INT,IN p_nuevoNumeroDeFactura INT,IN p_nuevoCodigoProducto int)
begin
    update DetalleFactura
    SET precioUnitario = p_nuevoPrecioUnitario,
        cantidad = p_nuevaCantidad,
        numeroDeFactura = p_nuevoNumeroDeFactura,
        codigoProducto = p_nuevoCodigoProducto
    WHERE codigoDetalleFactura = odigoDetalleFactura;
end$$
delimiter ;

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







-- ------------------------------------------------------------------------------- EmailProveedor --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarEmailProveedor (in mailProveedor varchar(50),in escripcion varchar(100),in odigoProveedor int)
begin
	insert into EmailProveedor ( EmailProveedor.emailProveedor, EmailProveedor.descripcion, EmailProveedor.codigoProveedor)
		values (mailProveedor,escripcion,odigoProveedor);
end $$        
delimiter ;
call sp_AgregarEmailProveedor('Luis@gmail.com','Correo de Proveedor Activo',1);
call sp_AgregarEmailProveedor('Maria@yahoo.com','Correo de Proveedor Activo',2);
call sp_AgregarEmailProveedor('Pedro@hotmail.com','Correo de Proveedor Activo',3);
call sp_AgregarEmailProveedor('Ana@outlook.com','Correo de Proveedor Activo',4);
call sp_AgregarEmailProveedor('Carlos@gmail.com','Correo de Proveedor Activo',5);
call sp_AgregarEmailProveedor('Sofia@yahoo.com','Correo de Proveedor Activo',6);
call sp_AgregarEmailProveedor('Juan@hotmail.com','Correo de Proveedor Inactivo',7);
call sp_AgregarEmailProveedor('Laura@outlook.com','Correo de Proveedor Activo',8);
call sp_AgregarEmailProveedor('Diego@gmail.com','Correo de Proveedor Activo',9);
call sp_AgregarEmailProveedor('Elena@yahoo.com','Correo de Proveedor Activo',10);
call sp_AgregarEmailProveedor('Roberto@hotmail.com','Correo de Proveedor Inactivo',11);

-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarEmailProveedor(in odigoEmailProveedor int)
begin
    delete from EmailProveedor
    where EmailProveedor.codigoEmailProveedor = odigoEmailProveedor;
end$$
delimiter ;
call sp_EliminarEmailProveedor ('11');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarEmailProveedor(in  odigoEmailProveedor int ,in mailProveedor varchar(50),in escripcion varchar(100),in odigoProveedor int)
begin
    update EmailProveedor
    set 
        EmailProveedor.emailProveedor =  mailProveedor ,
        EmailProveedor.descripcion = escripcion,
        EmailProveedor.codigoProveedor = odigoProveedor
        where
        EmailProveedor.codigoEmailProveedor = odigoEmailProveedor;
end$$
delimiter ;

-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarEmailProveedor()
begin 
	select
	e.codigoEmailProveedor ,
	e.emailProveedor  ,
	e.descripcion ,
	e.codigoProveedor 
    from emailProveedor e;
end $$        
delimiter ;
call sp_MostrarEmailProveedor;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarEmailProveedor(in odigoEmailProveedor int)
begin 
  select *from EmailProveedor where EmailProveedor.codigoEmailProveedor = odigoEmailProveedor;
end$$ 
delimiter ;
call sp_BuscarEmailProveedor(1);


-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



delimiter //
create function fn_TraerPrecioUnitario(p_codigoProducto varchar(15)) returns decimal(10,2)
deterministic
begin
	declare precio decimal(10,2);
	set precio= (select DetalleCompra.costoUnitario from DetalleCompra
    where DetalleCompra.codigoProducto=p_codigoProducto);
	return precio;
end //

delimiter ;




delimiter //
create trigger tr_insertarPreciosDetalleFactura_Before_Insert
before insert on DetalleFactura
for each row
	begin
		declare total decimal(10,2);
		
                set new.precioUnitario= (select precioUnitario from Productos
					where Productos.codigoProducto=new.codigoProducto);
        
	end //
delimiter ;


delimiter //
create trigger tr_insertarPreciosProductos_after_Insert
after insert on DetalleCompra
for each row
	begin
    call sp_actualizarPreciosProductos(new.codigoProducto, 
									(fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.40)),
									(fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.35)),
                                    (fn_TraerPrecioUnitario(new.codigoProducto)+(fn_TraerPrecioUnitario(new.codigoProducto)*0.25)),
                                    new.cantidad);
                                    
	end //
delimiter ;


delimiter //
create trigger tr_insertarTotalCompra_Before_Insert
after insert on DetalleCompra
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(costoUnitario*cantidad) from DetalleCompra where DetalleCompra.numeroDocumento=new.numeroDocumento));
    
    call sp_actualizarComprasTotal(new.numeroDocumento, total);
                                    
	end //
delimiter ;

delimiter //
create trigger tr_insertarTotalFactura_Before_Insert
after insert on DetalleFactura
for each row
	begin
    declare total decimal(10,2);
    
    set total=((select sum(precioUnitario*cantidad) from DetalleFactura where DetalleFactura.numeroDeFactura=new.numeroDeFactura ));
    
    call sp_actualizarFacturaTotal(new.numeroDeFactura, total);
                                    
	end //
delimiter ;

Alter user 'root'@'localhost' identified with mysql_native_password by 'ema22';

select * from Clientes;






































