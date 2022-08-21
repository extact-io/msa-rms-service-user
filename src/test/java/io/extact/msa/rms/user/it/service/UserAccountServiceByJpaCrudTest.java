package io.extact.msa.rms.user.it.service;

import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddConfig(key = "persistence.apiType", value = "jpa")
@ExtendWith(JulToSLF4DelegateExtension.class)
class UserAccountServiceByJpaCrudTest extends AbstractUserAccountServiceCrudTest {
}
