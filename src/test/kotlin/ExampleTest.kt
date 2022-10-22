import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExampleTest : StringSpec(
    {
        "hello" {
            1 shouldBe 1
        }
    }
)
