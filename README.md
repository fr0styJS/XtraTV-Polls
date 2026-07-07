# XtraTV

A remote-first Android TV / Fire TV fork of [Xtra](https://github.com/crackededed/Xtra), the Twitch client for Android.

Xtra runs on TV devices but its UI is built for touch. XtraTV makes the couch experience native:

- Left navigation rail (Games / Popular / Following / Saved / Search / Settings) instead of the phone bottom bar
- Full D-pad support: visible focus states on every card, no focus traps, tabs that switch as you move across them
- Remote player controls — center: play/pause, left/right: seek 15s in VODs and clips, up/down: control overlay, back: exit
- Search that opens and types with the TV keyboard
- Starts on Following → Channels so your channels are one click away

Phone behavior is unchanged; TV mode activates automatically on leanback devices.

## Install

```
gradlew assembleDebug
adb connect <tv-ip>:5555
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## License

[AGPL-3.0](LICENSE), same as upstream Xtra.
