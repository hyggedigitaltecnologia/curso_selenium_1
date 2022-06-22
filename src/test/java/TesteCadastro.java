import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteCadastro {

    private WebDriver driver;
    private  DSL dsl;

    @Before
    public void inicializa() {

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1200, 765));
        driver.get("file:///" + System.getProperty("user.dir") + "./src/main/resources/componentes.html");
        dsl = new DSL(driver);

    }

    @After
    public void finaliza() {

        driver.quit();

    }

    @Test
    public void deveRealizarCadastroComSucesso() {

        dsl.escrever("elementosForm:nome", "Jhonattan");
        dsl.escrever("elementosForm:sobrenome", "Gomes");
        dsl.clicarBotoes("elementosForm:sexo:0");
        driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
        new Select(driver.findElement(By.id("elementosForm:escolaridade"))).selectByVisibleText("Mestrado");
        new Select(driver.findElement(By.id("elementosForm:esportes"))).selectByVisibleText("Natacao");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Assert.assertTrue(driver.findElement(By.id("resultado")).getText().startsWith("Cadastrado!"));
        Assert.assertTrue(driver.findElement(By.id("descNome")).getText().endsWith("Jhonattan"));
        Assert.assertEquals("Sobrenome: Gomes", driver.findElement(By.id("descSobrenome")).getText());
        Assert.assertEquals("Sexo: Masculino", driver.findElement(By.id("descSexo")).getText());
        Assert.assertEquals("Comida: Pizza", driver.findElement(By.id("descComida")).getText());
        Assert.assertEquals("Escolaridade: mestrado", driver.findElement(By.id("descEscolaridade")).getText());
        Assert.assertEquals("Esportes: Natacao", driver.findElement(By.id("descEsportes")).getText());

    }

    @Test
    public void deveValidarNomeObrigatorio() {

        driver.findElement(By.id("elementosForm:cadastrar")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Nome eh obrigatorio", alert.getText());

    }

    @Test
    public void deveValidarSobrenomeObrigatorio() {

        driver.findElement(By.id("elementosForm:nome")).sendKeys("Jhonattan");
        driver.findElement(By.id("elementosForm:cadastrar")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Sobrenome eh obrigatorio", alert.getText());

    }

    @Test
    public void deveValidarSexoObrigatorio() {

        driver.findElement(By.id("elementosForm:nome")).sendKeys("Jhonattan");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Gomes");
        driver.findElement(By.id("elementosForm:cadastrar")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Sexo eh obrigatorio", alert.getText());

    }

    @Test
    public void deveValidarComidaVegetariana() {

        driver.findElement(By.id("elementosForm:nome")).sendKeys("Jhonattan");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Gomes");
        driver.findElement(By.id("elementosForm:sexo")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
        driver.findElement(By.id("elementosForm:cadastrar")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Tem certeza que voce eh vegetariano?", alert.getText());

    }

    @Test
    public void deveValidarEsportistaIndeciso() {

        driver.findElement(By.id("elementosForm:nome")).sendKeys("Jhonattan");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Gomes");
        driver.findElement(By.id("elementosForm:sexo")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
        Select combo = new Select(driver.findElement(By.id("elementosForm:esportes")));
        combo.selectByVisibleText("Karate");
        combo.selectByVisibleText("O que eh esporte?");
        driver.findElement(By.id("elementosForm:cadastrar")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Voce faz esporte ou nao?", alert.getText());

    }

}




