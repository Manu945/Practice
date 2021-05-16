package fr.manu.practice.tools;

import org.bukkit.inventory.ItemStack;

public enum HeadList {
    ALPHABET_X("NWE2Nzg3YmEzMjU2NGU3YzJmM2EwY2U2NDQ5OGVjYmIyM2I4OTg0NWU1YTY2YjVjZWM3NzM2ZjcyOWVkMzcifX19", "oak_wood_x"),
    ARROW_RIGHT("MTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19", "oak_wood_arrow_right"),
    ARROW_LEFT("YmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==", "oak_wood_arrow_left"),
    WOOD_WHAT("YmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19", "?"),
    SLOTS("ZWVhNzU5Zjk3OWI5YjhlYTgzMWNhN2UyZDY2ZGYxNDgyOTNmMWE1MTQ3OTgzYjUyYzQ4ZWZlMmMzMTVlIn19fQ==", "penguin"),
    TAUPE_GUN("MTgxODUyOTQzZTM2YzhhM2YxZTNkMGU0OTEyNTQ5Y2JjMjA1ZDk0NzM5NGFkYmU2NWY0ZDgxZDYxMWJlMmM4NyJ9fX0=", "mole"),
    LOUP_GAROU("MzY4ZDQzMTI5MzliYjMxMTFmYWUyOGQ2NWQ5YTMxZTc3N2Y4ZjJjOWZjNDI3NTAxY2RhOGZmZTNiMzY3NjU4In19fQ==", "werewolf"),
    DRAGON_BALL_1("NDkyOTlkYzAyYzM1ZjFiYzFhNjg5NWQ3ZmMyOGRlNzdjYTg5MGQwNjYzY2VjNWRjZDZlYTg0NjBhZjExMjEifX19", "dragon_ball_1"),
    DRAGON_BALL_2("ODA2YWM4MmUzYzc0MjdiYmNmMTU4MjFlODgyZDczYWViODBlZWJjYzZiNDU1ODI4MzI4YWViNzBkNzFhIn19fQ==", "dragon_ball_2"),
    DRAGON_BALL_3("Mzk5OTI1NGE3Y2E4ZDhiYTFmYWRkY2JhYjlkYTMyMzc0OWExYTBmNjVjODlhMDE2ZjY4MjM0Mjc2ODQ5NSJ9fX0=", "dragon_ball_3"),
    DRAGON_BALL_4("NmE0MzFhNWVlM2JlNzliOGViNTdhYjk1YzhjOTZkN2Q3NTE5MzJkNmRiZDkyNzI3ZjZlMzcyZTdjNWYifX19", "dragon_ball_4"),
    DRAGON_BALL_5("NGI3MzI1N2U5YmM0N2JjY2JhZmFhNTQzNzNjYTExYjg3NWU1YWMzNWM5ZDU5NzNiNTgxMDU0Y2M5YmJhODgifX19", "dragon_ball_5"),
    DRAGON_BALL_6("YjdmYjZhNWFkYTQ3MDU2YzhiZjk3NTY2NDk4ZjVlYTQxNzMzMzlmYTc4MTljYmNlOTcwMDllOTA1MGRlIn19fQ==", "dragon_ball_6"),
    DRAGON_BALL_7("NWMxYTdlMTkzZjM3YzJjNTRlMzU4ZjIyYThhMmQwMjg5NzkzZGQzYjJkNmM3OTllODQyNGI5MjZhMzk1MSJ9fX0=", "dragon_ball_7"),
    CHEST("YzM5MGVlZGUzODFiYjg0NDdmN2Q3MmUxNWI1NjM0NzY4M2UwMmMxN2M5YjhmYzZiZWNkNzI2ZjBhNTJjN2ZjMSJ9fX0=", "chest"),
    ENDERCHEST("MWJlZWNhZjUzMjRmOGJiNjJiMTNhNGM0NjUwNThiNTEyZDM3YjlhNDgyZjVmMjQ4ODY1N2YxNjQxNDc1YmIyOSJ9fX0=", "enderchest");

    private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

    private final ItemStack item;

    private final String idTag;

    HeadList(String texture, String id) {
        this.item = CreateSkull.createSkull(this.prefix + texture, id);
        this.idTag = id;
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public String getName() {
        return this.idTag;
    }
}
