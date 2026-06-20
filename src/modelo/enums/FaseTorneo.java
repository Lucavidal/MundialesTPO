package modelo.enums;





public enum FaseTorneo {
    GRUPOS,
    DIECISEISAVOS,   // Ronda de 32 (32 equipos -> 16 partidos)
    OCTAVOS,         // Ronda de 16 (16 equipos -> 8 partidos)
    CUARTOS,         // Cuartos de final (8 -> 4)
    SEMIFINAL,       // Semifinal (4 -> 2)
    TERCER_PUESTO,   // Partido por el tercer puesto (2 perdedores de semis)
    FINAL            // Final (2 ganadores de semis -> campeon)
}