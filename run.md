export JAVA_HOME=/opt/homebrew/opt/openjdk@21 && export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH" && ./gradlew assembleDebug

./gradlew assembleDebug

./gradlew assembleRelease


git add -A && git commit -m "chore(release): v2.0.0 – local-only ExoPlayer, progressive loading, caching, docs + CI updated"

git tag -a v2.0.0 -m "YouKids 2.0.0: Local-only ExoPlayer, progressive loading, caching" && git push && git push origin v2.0.0