DROP database IF EXISTS smartrestaurant;
CREATE database smartrestaurant;
use smartrestaurant;

CREATE TABLE Account (
	username varchar(50) primary key not null,
    password varbinary(100) not null,
    nome varchar(70) not null,
    tipo int not null
);

CREATE TABLE Tavolo (
	username varchar(50) primary key not null,
    password varbinary(100) not null,
    nome varchar(70) not null,
	libero boolean not null,
    posti int not null,
    vuole_pagare boolean
);

CREATE TABLE Anagrafica (
	username varchar(50) primary key not null,
	cognome varchar(50) not null,
    dataDiNascita varchar(10) not null,
    titoli varchar(255) not null,
    esperienze varchar(255) not null,
    foreign key (username) references Account(username) on delete cascade on update cascade
);

CREATE TABLE Comanda (
	id bigint primary key not null auto_increment,
    username varchar(50) not null,
    stato boolean not null,
    data varchar(16) not null,
    recensione varchar(255),
    totale float not null,
    foreign key (username) references Tavolo(username) on delete cascade on update cascade
);

CREATE TABLE Piatto (
	id bigint primary key not null auto_increment,
    nome varchar(255) not null,
    categoria varchar(255) not null,
    ingredienti varchar(255) not null,
    prezzo float not null,
    foto varchar(255) not null,
    descrizione varchar(255) not null
);

CREATE TABLE PiattoOrdinato (
	id bigint primary key not null auto_increment,
    id_piatto bigint not null,
    comanda bigint not null,
    stato boolean not null,
    data varchar(16) not null,
    quantita int not null,
    note varchar(255),
    foreign key (comanda) references Comanda(id) on delete cascade on update cascade,
    foreign key (id_piatto) references Piatto(id) on update cascade
);

CREATE TABLE Notifica (
	id int primary key not null auto_increment,
    username varchar(50) not null,
	categoria varchar(255) not null,
    mittente varchar(255) not null,
    testo varchar(255) not null,
    letta boolean not null,
    data varchar(16) not null,
	foreign key (username) references Account(username) on delete cascade on update cascade
);