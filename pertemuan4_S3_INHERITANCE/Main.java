/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pertemuan4_S3_INHERITANCE;

/**
 *
 * @author USER
 */
public class Main {
    public static void main(String[] args) {
        Enemy moster = new Enemy();
        Zombie zumbi = new Zombie();
        Pocong hantuPocong = new Pocong();
        Burung garuda = new Burung();
        
        moster.name = "Mathilda";
        moster.hp = 100;
        moster.attackPoint = 200;
        System.out.println("==========================");
        System.out.println("Name = " + moster.name + "\nHP = " + moster.hp + "\nAttack Point = " + moster.attackPoint);
        moster.attack();
        
        zumbi.name = "Alaska";
        zumbi.hp = 100;
        zumbi.attackPoint = 50;
        System.out.println("==========================");
        System.out.println("Name = " + zumbi.name + "\nHP = " + zumbi.hp + "\nAttackPoint = " + zumbi.attackPoint);
        zumbi.attack();
        zumbi.walk();
        
        hantuPocong.name = "Sumi";
        hantuPocong.hp = 100;
        hantuPocong.attackPoint = 40;
        System.out.println("==========================");
        System.out.println("Name = " + hantuPocong.name + "\nHP = " + hantuPocong.hp + "\nAttack Point = " + hantuPocong.attackPoint);
        hantuPocong.attack();
        hantuPocong.jump();
        
        garuda.name = "Beo";
        garuda.hp = 100;
        garuda.attackPoint = 20;
        System.out.println("==========================");
        System.out.println("Name = " + garuda.name + "\nHP = " + garuda.hp + "\nAttack Point = " + garuda.attackPoint);
        garuda.attack();
        garuda.walk();
        garuda.jump();
        garuda.fly();
        System.out.println("==========================");
                
    }
}
