package so.a56143974;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DnDProgram {

    public static void createMonsterFile() {
        File monsterFile = new File("./monster_table.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(monsterFile))) {
            writer.write("MonsterName HitPoints HitDamage\nHydra 50 6\nCyclops 20 1\nGiant Snake 5 1\nPhred 100 5\nPurple Monkey 10 2\nIce Golem 25 10\nHeat Miser 50 2\nSquirrel 2 5\nWhite Whale 80 4\nSmurf 5 2");
            writer.flush();
            System.out.println("Monster table successfully loaded.\n");
        } catch (IOException e) {
            System.out.println("That's a pretty dank error mate.\nSeems as if you can't write your monster table.\n\nOuch.\n" + e);
        }
    }
}