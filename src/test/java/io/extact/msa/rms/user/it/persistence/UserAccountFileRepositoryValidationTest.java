package io.extact.msa.rms.user.it.persistence;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest(resetPerTest = true)
@AddConfig(key = "persistence.apiType", value = "file")
@AddConfig(key = "csv.type", value = "temporary")
class UserAccountFileRepositoryValidationTest extends AbstractUserAccountRepositoryValidationTest {
}
