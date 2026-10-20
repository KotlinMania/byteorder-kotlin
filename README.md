# byteorder-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fbyteorder-kotlin-blue.svg)](https://github.com/KotlinMania/byteorder-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/byteorder-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/byteorder-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/byteorder-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/byteorder-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`byteorder`](https://github.com/BurntSushi/byteorder).

**Original Project:** This port is based on [`byteorder`](https://github.com/BurntSushi/byteorder). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `byteorder`

> The text below is reproduced from [`https://github.com/BurntSushi/byteorder`](https://github.com/BurntSushi/byteorder). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs where appropriate so they continue to resolve from this repository.

byteorder
=========
This crate provides convenience methods for encoding and decoding
numbers in either big-endian or little-endian order.

[![Build status](https://github.com/BurntSushi/byteorder/workflows/ci/badge.svg)](https://github.com/BurntSushi/byteorder/actions)
[![](https://meritbadge.herokuapp.com/byteorder)](https://crates.io/crates/byteorder)

Dual-licensed under MIT or the [UNLICENSE](https://unlicense.org/).


### Documentation

https://docs.rs/byteorder


### Installation

This crate works with Cargo and is on
[crates.io](https://crates.io/crates/byteorder). Add it to your `Cargo.toml`
like so:

```toml
[dependencies]
byteorder = "1"
```

If you want to augment existing `Read` and `Write` traits, then import the
extension methods like so:

```rust
use byteorder::{ReadBytesExt, WriteBytesExt, BigEndian, LittleEndian};
```

For example:

```rust
use std::io::Cursor;
use byteorder::{BigEndian, ReadBytesExt};

let mut rdr = Cursor::new(vec![2, 5, 3, 0]);
// Note that we use type parameters to indicate which kind of byte order
// we want!
assert_eq!(517, rdr.read_u16::<BigEndian>().unwrap());
assert_eq!(768, rdr.read_u16::<BigEndian>().unwrap());
```

### `no_std` crates

This crate has a feature, `std`, that is enabled by default. To use this crate
in a `no_std` context, add the following to your `Cargo.toml`:

```toml
[dependencies]
byteorder = { version = "1", default-features = false }
```


### Alternatives

Note that as of Rust 1.32, the standard numeric types provide built-in methods
like `to_le_bytes` and `from_le_bytes`, which support some of the same use
cases.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:byteorder-kotlin:2.0.1")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same license terms as the upstream [`byteorder`](https://github.com/BurntSushi/byteorder). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the byteorder authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`byteorder`](https://github.com/BurntSushi/byteorder) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
