package io.extact.msa.rms.user.it.service;

import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.extact.msa.rms.test.utils.ClearOpenTelemetryContextCdiExtension;
import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.AddExtension;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddExtension(ClearOpenTelemetryContextCdiExtension.class)
@AddConfig(key = "rms.persistence.apiType", value = "file")
@AddConfig(key = "rms.persistence.csv.type", value = "temporary")
@ExtendWith(JulToSLF4DelegateExtension.class)
class UserAccountServiceByTemporaryFileCrudTest extends AbstractUserAccountServiceCrudTest {
}
