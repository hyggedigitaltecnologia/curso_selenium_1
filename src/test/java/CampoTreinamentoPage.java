import org.openqa.selenium.WebDriver;

public class CampoTreinamentoPage {

    private  DSL dsl;

    public CampoTreinamentoPage(WebDriver driver) {
        dsl = new DSL(driver);
    }

    public void setName(String nome) {
        dsl.escrever("elementosForm:nome", nome);
    }

}
