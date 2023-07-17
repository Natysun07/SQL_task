package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SqlHelper;
import ru.netology.sql.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.sql.data.SqlHelper.cleanData;

public class SQL_taskTest {
    private LoginPage loginPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterAll
    static void cleanUp() {
        cleanData();
    }

    @Test
    public void shouldSuccessLogin() {
        var authInfo = DataHelper.getAuthInfoTesting();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    public void shouldGetNotificationWithUnregisteredUser() {
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.setErrorNotification();
    }

    @Test
    public void shouldGetNotificationWithIncorrectCode() {
        var authInfo = DataHelper.getAuthInfoTesting();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.errorNotificationVisibility();
    }
}
