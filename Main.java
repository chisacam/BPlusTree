import java.io.File;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        DataNode dataroot = null;
        IndexNode indexroot = null;
        BPlusTree bptree = new BPlusTree();
        ManageNode mngnode = new ManageNode();
        Scanner scanner = new Scanner(System.in);
        File bakfile = new File("bptree.ser");
        String menusel;
        byte var9;
        if (bakfile.isFile()) {
            System.out.println("백업파일을 찾았습니다. 백업파일로 복원하시려면 y, 아니면 n을 입력해주세요.");
            menusel = scanner.nextLine();
            var9 = -1;
            switch(menusel.hashCode()) {
            case 110:
                if (menusel.equals("n")) {
                    var9 = 1;
                }
                break;
            case 121:
                if (menusel.equals("y")) {
                    var9 = 0;
                }
            }

            switch(var9) {
            case 0:
                System.out.println("복원을 선택하셨습니다. 복원을 시작합니다.");
                indexroot = bptree.fileToTree();
                System.out.println("복원이 완료되었습니다.");
                break;
            case 1:
                System.out.println("복원을 거부하셨습니다. 트리를 새로 구성합니다.");
                break;
            default:
                System.out.println("잘못된 명령입니다. 트리를 새로 구성합니다.");
            }
        } else {
            File dir = new File("news/");
            File[] files = dir.listFiles();

            assert files != null;

            String[] fileString = new String[files.length];
            System.out.print("백업파일을 찾지못했거나, 복원을 거부하셨습니다. B+트리 구성을 시작합니다.");

            try {
                for(int i = 0; i < files.length; ++i) {
                    if (files[i].getName().endsWith(".txt")) {
                        Scanner scan = new Scanner(files[i]);
                        if (scan.hasNextLine()) {
                            for(fileString[i] = scan.nextLine(); scan.hasNextLine(); fileString[i] = fileString[i] + scan.nextLine()) {
                            }

                            fileString[i] = fileString[i].replaceAll("[^ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\\s|⺀-\u2eff|㇀-\u31ef|㈀-㋿|㐀-\u4dbf|一-龿|豈-\ufaff|\u3040-ゟ|゠-ヿ|ヰ-ㇿ]", "");
                            String[] wordString = fileString[i].split("\\s+");
                            String filename = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                            String[] var14 = wordString;
                            int var15 = wordString.length;

                            for(int var16 = 0; var16 < var15; ++var16) {
                                String aWordString = var14[var16];
                                if (indexroot == null) {
                                    WordNode secworddata;
                                    if (dataroot == null) {
                                        dataroot = new DataNode();
                                        secworddata = new WordNode(aWordString);
                                        secworddata.insertDocNode(filename);
                                        dataroot.setWorddata(secworddata);
                                    } else if (!mngnode.isDataFull(dataroot)) {
                                        secworddata = new WordNode(aWordString);
                                        secworddata.insertDocNode(filename);
                                        dataroot.setWorddata(secworddata);
                                        if (mngnode.isDataFull(dataroot)) {
                                            indexroot = new IndexNode();
                                            indexroot = mngnode.splitDataNode(indexroot, dataroot);
                                        }
                                    }
                                } else {
                                    indexroot = bptree.insertNode(indexroot, aWordString, filename);
                                }
                            }
                        }
                    }
                }
            } catch (Exception var19) {
                var19.printStackTrace();
            }
        }

        if (indexroot != null) {
            System.out.println("B+트리 구성이 완료되었습니다.");
        } else {
            System.out.println("B+트리 구성중 에러가 발생하였습니다. 터미널 에러 로그를 참조해주세요.");
        }

        if (!bakfile.isFile()) {
            System.out.println("백업파일을 찾지못했습니다. 백업하시려면 y, 아니면 n을 입력해주세요.");
            menusel = scanner.nextLine();
            var9 = -1;
            switch(menusel.hashCode()) {
            case 110:
                if (menusel.equals("n")) {
                    var9 = 1;
                }
                break;
            case 121:
                if (menusel.equals("y")) {
                    var9 = 0;
                }
            }

            switch(var9) {
            case 0:
                System.out.println("백업을 선택하셨습니다. 백업을 시작합니다.");
                bptree.treeToFile(indexroot);
                System.out.println("백업이 완료되었습니다.");
                break;
            case 1:
                System.out.println("백업을 거부하셨습니다.");
                break;
            default:
                System.out.println("잘못된 명령입니다. 백업을 하지않습니다.");
            }
        }

        while(true) {
            while(true) {
                System.out.println("원하는 메뉴를 입력한뒤 엔터를 눌러주세요.\n검색\n종료\n");
                menusel = scanner.nextLine();
                if (menusel.equals("종료")) {
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return;
                }

                var9 = -1;
                switch(menusel.hashCode()) {
                case 1418313:
                    if (menusel.equals("검색")) {
                        var9 = 0;
                    }
                    break;
                case 3556498:
                    if (menusel.equals("test")) {
                        var9 = 1;
                    }
                }

                switch(var9) {
                case 0:
                    System.out.println("검색할 단어를 입력해주세요.");
                    String inputword = scanner.nextLine();
                    String[] userwordstring = inputword.split(" ");

                    for(int j = 0; j < userwordstring.length; ++j) {
                        bptree.searchLeaf(indexroot, userwordstring[j]);
                    }

                    bptree.printArrayList();
                    break;
                case 1:
                    bptree.printIndexNode(indexroot);
                    bptree.printDataNode(indexroot);
                    break;
                default:
                    System.out.println("잘못된 명령어입니다. 다시 시도해주세요.");
                }
            }
        }
    }
}
