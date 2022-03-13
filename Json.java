package fr.manu.ssuhc.utils.message;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Json {
    public static class UtilActionMessage {
        private List<AMText> Text = new ArrayList<>();

        public AMText addText(String Message) {
            AMText Text = new AMText(Message);
            this.Text.add(Text);
            return Text;
        }

        private String getFormattedMessage() {
            StringBuilder Chat = new StringBuilder("[\"\",");
            for (AMText Text : this.Text)
                Chat.append(Text.getFormattedMessage()).append(",");
            Chat = new StringBuilder(Chat.substring(0, Chat.length() - 1));
            Chat.append("]");
            return Chat.toString();
        }

        public void sendToPlayer(Player player) {
            try {
                Object base;
                String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutChat").getConstructor(new Class[] { getNMSClass("IChatBaseComponent") });
                if (version.contains("v1_7") || version.contains("1_8_R1")) {
                    base = getNMSClass("ChatSerializer").getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { getFormattedMessage() });
                } else {
                    base = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { getFormattedMessage() });
                }
                Object packet = titleConstructor.newInstance(new Object[] { base });
                sendPacket(player, packet);
            } catch (Exception exception) {}
        }

        private void sendPacket(Player player, Object packet) {
            try {
                Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
                Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
                playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
            } catch (Exception exception) {}
        }

        private Class<?> getNMSClass(String name) {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            try {
                return Class.forName("net.minecraft.server." + version + "." + name);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        public enum ClickableType {
            RunCommand("run_command"),
            SuggestCommand("suggest_command"),
            OpenURL("open_url");

            public String Action;

            ClickableType(String Action) {
                this.Action = Action;
            }
        }

        public class AMText {
            private String Message = "";

            private Map<String, Map.Entry<String, String>> Modifiers = new HashMap<>();

            AMText(String Text) {
                this.Message = Text;
            }

            String getFormattedMessage() {
                StringBuilder Chat = new StringBuilder("{\"text\":\"" + this.Message + "\"");
                for (String Event : this.Modifiers.keySet()) {
                    Map.Entry<String, String> Modifier = this.Modifiers.get(Event);
                    Chat.append(",\"").append(Event).append("\":{\"action\":\"").append(Modifier.getKey())
                            .append("\",\"value\":").append(Modifier.getValue()).append("}");
                }
                Chat.append("}");
                return Chat.toString();
            }

            public AMText addHoverText(String... Text) {
                StringBuilder Value;
                String Event = "hoverEvent";
                String Key = "show_text";
                if (Text.length == 1) {
                    Value = new StringBuilder("{\"text\":\"" + Text[0] + "\"}");
                } else {
                    Value = new StringBuilder("{\"text\":\"\",\"extra\":[");
                    String[] arrayOfString;
                    int j = (arrayOfString = Text).length;
                    for (int i = 0; i < j; i++) {
                        String Message = arrayOfString[i];
                        Value.append("{\"text\":\"").append(Message).append("\"},");
                    }
                    Value = new StringBuilder(Value.substring(0, Value.length() - 1));
                    Value.append("]}");
                }
                Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, Value.toString());
                this.Modifiers.put(Event, Values);
                return this;
            }

            public void setClickEvent(Json.UtilActionMessage.ClickableType Type, String Value) {
                String Event = "clickEvent";
                String Key = Type.Action;
                Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, "\"" + Value + "\"");
                this.Modifiers.put(Event, Values);
            }
        }
    }

    public enum ClickableType {
        RunCommand("run_command"),
        SuggestCommand("suggest_command"),
        OpenURL("open_url");

        public String Action;

        ClickableType(String Action) {
            this.Action = Action;
        }
    }

    public class AMText {
        private String Message = "";

        private Map<String, Map.Entry<String, String>> Modifiers = new HashMap<>();

        AMText(String Text) {
            this.Message = Text;
        }

        String getFormattedMessage() {
            StringBuilder Chat = new StringBuilder("{\"text\":\"" + this.Message + "\"");
            for (String Event : this.Modifiers.keySet()) {
                Map.Entry<String, String> Modifier = this.Modifiers.get(Event);
                Chat.append(",\"").append(Event).append("\":{\"action\":\"").append(Modifier.getKey())
                        .append("\",\"value\":").append(Modifier.getValue()).append("}");
            }
            Chat.append("}");
            return Chat.toString();
        }

        AMText addHoverText(String... Text) {
            StringBuilder Value;
            String Event = "hoverEvent";
            String Key = "show_text";
            if (Text.length == 1) {
                Value = new StringBuilder("{\"text\":\"" + Text[0] + "\"}");
            } else {
                Value = new StringBuilder("{\"text\":\"\",\"extra\":[");
                String[] arrayOfString;
                int j = (arrayOfString = Text).length;
                for (int i = 0; i < j; i++) {
                    String Message = arrayOfString[i];
                    Value.append("{\"text\":\"").append(Message).append("\"},");
                }
                Value = new StringBuilder(Value.substring(0, Value.length() - 1));
                Value.append("]}");
            }
            Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, Value.toString());
            this.Modifiers.put(Event, Values);
            return this;
        }

        public void setClickEvent(Json.ClickableType Type, String Value) {
            String Event = "clickEvent";
            String Key = Type.Action;
            Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, "\"" + Value + "\"");
            this.Modifiers.put(Event, Values);
        }
    }
}