package io.extact.msa.rms.user.it.persistence;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest(resetPerTest = true)
@AddConfig(key = "persistence.apiType", value = "jpa")
class UserAccountJpaRepositoryValidationTest extends AbstractUserAccountRepositoryValidationTest {
}
