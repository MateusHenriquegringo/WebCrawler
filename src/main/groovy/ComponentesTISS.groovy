import java.time.LocalDate

class ComponentesTISS {

    String competencia;
    String publicacao;
    String inicioVigencia;

    ComponentesTISS(String competencia, String publicacao, String inicioVigencia ) {
        this.publicacao = publicacao
        this.inicioVigencia = inicioVigencia
        this.competencia = competencia
    }

    String getCompetencia() {
        return competencia
    }

    void setCompetencia(String competencia) {
        this.competencia = competencia
    }

    String getInicioVigencia() {
        return inicioVigencia
    }

    void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia
    }

    String getPublicacao() {
        return publicacao
    }

    void setPublicacao(Date publicacao) {
        this.publicacao = publicacao
    }



}
