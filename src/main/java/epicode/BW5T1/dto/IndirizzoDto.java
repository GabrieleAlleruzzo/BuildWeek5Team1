package epicode.BW5T1.dto;

public class IndirizzoDto{

    private Long id;
    private String via;
    private String civico;
    private String localita;
    private String cap;

    private Long idComune;
    private String nomeComune;
    private String nomeProvincia;
    private String siglaProvincia;


    public IndirizzoDto(Indirizzo indirizzo) {
        this.id = indirizzo.getId();
        this.via = indirizzo.getVia();
        this.civico = indirizzo.getCivico();
        this.localita = indirizzo.getLocalita();
        this.cap = indirizzo.getCap();

        if (indirizzo.getComune() != null) {
            this.idComune = indirizzo.getComune().getId();
            this.nomeComune = indirizzo.getComune().getNome();

            if (indirizzo.getComune().getProvincia() != null) {
                this.nomeProvincia = indirizzo.getComune().getProvincia().getNome();
                this.siglaProvincia = indirizzo.getComune().getProvincia().getSigla();
            }
        }
    }

    // Getters e Setters

    // (Puoi generare con Lombok: @Getter, @Setter oppure IDE)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }

    public String getCivico() { return civico; }
    public void setCivico(String civico) { this.civico = civico; }

    public String getLocalita() { return localita; }
    public void setLocalita(String localita) { this.localita = localita; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public Long getIdComune() { return idComune; }
    public void setIdComune(Long idComune) { this.idComune = idComune; }

    public String getNomeComune() { return nomeComune; }
    public void setNomeComune(String nomeComune) { this.nomeComune = nomeComune; }

    public String getNomeProvincia() { return nomeProvincia; }
    public void setNomeProvincia(String nomeProvincia) { this.nomeProvincia = nomeProvincia; }

    public String getSiglaProvincia() { return siglaProvincia; }
    public void setSiglaProvincia(String siglaProvincia) { this.siglaProvincia = siglaProvincia; }
}