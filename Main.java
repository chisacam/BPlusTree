import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataNode dataroot = null;
        IndexNode indexroot = null;
        BPlusTree bptree = new BPlusTree();
        ManageNode mngnode = new ManageNode();
        Scanner scanner = new Scanner(System.in);
        File dir = new File("news/");
        File[] files = dir.listFiles();
        assert files != null;
        String[] fileString = new String[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                if(!files[i].getName().endsWith(".txt")){continue;}
                Scanner scan = new Scanner(files[i]);
                if(!scan.hasNextLine()){continue;}
                fileString[i] = scan.nextLine();
                while (scan.hasNextLine()) {
                    fileString[i] += scan.nextLine();
                }
                fileString[i] = fileString[i].replaceAll("[^ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\\s|\u2E80-\u2EFF|\u31C0-\u31EF|\u3200-\u32FF|\u3400-\u4DBF|\u4E00-\u9FBF|\uF900-\uFAFF|\u3040-\u309F|\u30A0-\u30FF|\u30F0-\u31FF]", "");
                String[] wordString = fileString[i].split("\\s+");
                String filename = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                for (String aWordString : wordString) {
                    if(indexroot == null) {
                        if (dataroot == null) {
                            dataroot = new DataNode();
                            WordNode firstworddata = new WordNode(aWordString);
                            firstworddata.insertDocNode(filename);
                            dataroot.setWorddata(firstworddata);
                        } else {
                            if(!mngnode.isDataFull(dataroot)) {
                                WordNode secworddata = new WordNode(aWordString);
                                secworddata.insertDocNode(filename);
                                dataroot.setWorddata(secworddata);
                                if(mngnode.isDataFull(dataroot)){
                                    indexroot = new IndexNode();
                                    indexroot = mngnode.splitDataNode(indexroot, dataroot);
                                }
                            }
                        }
                    }else{
                        indexroot = bptree.insertNode(indexroot, aWordString, filename);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("원하는 메뉴를 입력한뒤 엔터를 눌러주세요.\n" +
                    "검색\n" +
                    "종료\n");
            String menusel = scanner.nextLine();
            if(menusel.equals("종료")){
                System.out.println("프로그램을 종료합니다.");
                scanner.close();
                break;
            }
            switch (menusel) {
                case "검색":
                    System.out.println("검색할 단어를 입력해주세요.");
                    String inputword = scanner.nextLine();
                    String[] userwordstring = inputword.split(" ");
                    for(int j = 0; j < userwordstring.length;j++) {

                    }
                    break;
                case "test":
                    bptree.printIndexNode(indexroot);
                    bptree.printDataNode(indexroot);
                    break;
                default:
                    System.out.println("잘못된 명령어입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }
}
