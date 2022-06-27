import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteCadastro {

    private WebDriver driver;
    private  DSL dsl;
    private CampoTreinamentoPage page;

    @Before
    public void inicializa() {

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1200, 765));
        driver.get("file:///" + System.getProperty("user.dir") + "./src/main/resources/componentes.html");
        dsl = new DSL(driver);
        page = new CampoTreinamentoPage(driver);

    }

    @After
    public void finaliza() {

        driver.quit();

    }

    @Test
    public void deveRealizarCadastroComSucesso() {

        page.setName("Jhonattan");
        page.setSobrenome("Gomes");
        page.setSexoMasculino();
        page.setComidaPizza();
        page.setEscolaridade("Mestrado");
        page.setEsporte("Natacao");
        page.cadastrar();

        Assert.assertTrue(page.obterResultadoCadastro().startsWith("Cadastrado!"));
        Assert.assertTrue(page.obterNomeCadastro().endsWith("Jhonattan"));;
        Assert.assertEquals("Sobrenome: Gomes", page.obterSobreNomeCdastro());
        Assert.assertEquals("Sexo: Masculino", page.obterSexoCdastro());
        Assert.assertEquals("Comida: Pizza", page.obterComidaCdastro());
        Assert.assertEquals("Escolaridade: mestrado", page.obterEscolaridadeCdastro());
        Assert.assertEquals("Esportes: Natacao", page.obterEsporteCdastro());

    }

    @Test
    public void deveValidarNomeObrigatorio() {

        page.cadastrar();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Nome eh obrigatorio", dsl.alertaObterTextoEAceita());

    }

    @Test
    public void deveValidarSobrenomeObrigatorio() {

        page.setName("Jhonattan");
        page.cadastrar();
        Assert.assertEquals("Sobrenome eh obrigatorio", dsl.alertaObterTextoEAceita());

    }

    @Test
    public void deveValidarSexoObrigatorio() {

        page.setName("Jhonattan");
        page.setSobrenome("Gomes");
        page.cadastrar();
        Assert.assertEquals("Sexo eh obrigatorio", dsl.alertaObterTextoEAceita());

    }

    @Test
    public void deveValidarComidaVegetariana() {

        page.setName("Jhonattan");
        page.setSobrenome("Gomes");
        page.setSexoFeminino();
        page.setComidaCarne();
        page.setComidaVegetariano();
        page.cadastrar();
        Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.alertaObterTextoEAceita());

    }

    @Test
    public void deveValidarEsportistaIndeciso() {

        page.setName("Jhonattan");
        page.setSobrenome("Gomes");
        page.setSexoFeminino();
        page.setComidaCarne();
        page.setEsporte("Karate", "O que eh esporte?");
        page.cadastrar();
        Assert.assertEquals("Voce faz esporte ou nao?", dsl.alertaObterTextoEAceita());

    }

}




