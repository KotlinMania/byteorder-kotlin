import importlib.util
import unittest
from pathlib import Path

spec = importlib.util.spec_from_file_location("guard", Path(__file__).with_name("check_swift_tests.py"))
guard = importlib.util.module_from_spec(spec)
spec.loader.exec_module(guard)


def passing_log():
    lines = [f'Test "{label}" passed after 0.01 seconds.' for label in guard.CASES.values()]
    lines.append("Test run with 3 tests passed after 0.03 seconds.")
    for module in ["Byteorder", "KotlinRuntimeSupport", "KotlinCoroutineSupport", "SwiftTestHarnessTests", "SwiftTestHarnessPackageTests"]:
        mode = " -swift-version 6" if module == "SwiftTestHarnessTests" else ("" if module == "SwiftTestHarnessPackageTests" else " -swift-version 5")
        lines.append("swift-frontend -module-name " + module + " -warnings-as-errors -target arm64-apple-macosx15.0" + mode)
    return "\n".join(lines)


class SwiftGuardTests(unittest.TestCase):
    def test_accepts_three_named_cases_and_actual_mode_policy(self):
        self.assertEqual(guard.verify(passing_log())["actual_behavior_tests"], 3)

    def test_zero_cases_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log().replace("with 3 tests", "with 0 tests"))

    def test_missing_case_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log().replace(next(iter(guard.CASES.values())), "Unrelated behavior"))

    def test_duplicate_case_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log() + '\nTest "Unsigned endian encoding crosses Swift boundary" passed')

    def test_failed_or_skipped_case_rejected(self):
        for verdict in ["failed", "skipped"]:
            with self.subTest(verdict=verdict), self.assertRaises(ValueError):
                guard.verify(passing_log() + "\nTest signedWidthRoundTrip() " + verdict)

    def test_dependency_swift_six_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log().replace("-swift-version 5", "-swift-version 6"))

    def test_authored_swift_five_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log().replace("-swift-version 6", "-swift-version 5"))

    def test_missing_werror_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log().replace("-warnings-as-errors", ""))

    def test_wrong_deployment_target_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify(passing_log().replace("macosx15.0", "macosx14.0"))

    def test_missing_product_frontend_rejected(self):
        with self.assertRaises(ValueError):
            guard.verify("\n".join(x for x in passing_log().splitlines() if "-module-name Byteorder " not in x))


if __name__ == "__main__":
    unittest.main()
