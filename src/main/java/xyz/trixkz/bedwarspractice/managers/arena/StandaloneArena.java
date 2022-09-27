package xyz.trixkz.bedwarspractice.managers.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StandaloneArena {

    private CustomLocation a;

    private CustomLocation min;
    private CustomLocation max;
}
