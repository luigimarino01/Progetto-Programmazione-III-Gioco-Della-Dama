CREATE TABLE Giocatore(
    nome varchar(30) NOT NULL,
    cognome varchar(30) NOT NULL,
    nickname varchar(20) NOT NULL,
    email varchar(20) NOT NULL,
    numeroVittorie int NOT NULL,
    PRIMARY KEY(nickname)
)
;
CREATE TABLE Pedina(
    idPedina int NOT NULL,
    x int NOT NULL,
    y int NOT NULL,
    PRIMARY KEY(idPedina)
)
;
CREATE TABLE Partita(
    idPartita int NOT NULL AUTO_INCREMENT,
    nickname varchar(20) NOT NULL,
    finita int NOT NULL,
    turnoGiocatore int NOT NULL,
    squadraGiocatore int NOT NULL, --1 rosso / 0 nero--
    PRIMARY KEY(idPartita),
    FOREIGN KEY(nickname) REFERENCES Giocatore(nickname)
)
;
CREATE TABLE Contiene(
    idPartita int NOT NULL,
    idPedina int NOT NULL,
    x int NOT NULL,
    y int NOT NULL,
    viva int NOT NULL,
    dama int NOT NULL,
    colore int NOT NULL,
    PRIMARY KEY(idPartita, idPedina),
    FOREIGN KEY(idPartita) REFERENCES Partita(idPartita),
    FOREIGN KEY(idPedina) REFERENCES Pedina(idPedina)
)
;


INSERT INTO Pedina VALUES (1,0,0);
INSERT INTO Pedina VALUES (2,0,0);
INSERT INTO Pedina VALUES (3,0,0);
INSERT INTO Pedina VALUES (4,0,0);
INSERT INTO Pedina VALUES (5,0,0);
INSERT INTO Pedina VALUES (6,0,0);
INSERT INTO Pedina VALUES (7,0,0);
INSERT INTO Pedina VALUES (8,0,0);
INSERT INTO Pedina VALUES (9,0,0);
INSERT INTO Pedina VALUES (10,0,0);
INSERT INTO Pedina VALUES (11,0,0);
INSERT INTO Pedina VALUES (12,0,0);
INSERT INTO Pedina VALUES (13,0,0);
INSERT INTO Pedina VALUES (14,0,0);
INSERT INTO Pedina VALUES (15,0,0);
INSERT INTO Pedina VALUES (16,0,0);
INSERT INTO Pedina VALUES (17,0,0);
INSERT INTO Pedina VALUES (18,0,0);
INSERT INTO Pedina VALUES (19,0,0);
INSERT INTO Pedina VALUES (20,0,0);
INSERT INTO Pedina VALUES (21,0,0);
INSERT INTO Pedina VALUES (22,0,0);
INSERT INTO Pedina VALUES (23,0,0);
INSERT INTO Pedina VALUES (24,0,0);