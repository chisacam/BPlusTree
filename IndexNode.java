public class IndexNode {
    private String[] words;
    private int countwords;
    private int countilink;
    private int countdlink;
    private boolean hasdatalink;
    IndexNode ileftlink;
    IndexNode imidlink;
    IndexNode irightlink;
    IndexNode itemplink;
    IndexNode parent;
    DataNode dleftlink;
    DataNode dmidlink;
    DataNode drightlink;
    DataNode dtemplink;
    //인덱스노드 생성자
    public IndexNode(){
        this.words = new String[3]; //세번째 배열은 임시 저장소
        this.hasdatalink = false;
        this.countwords = 0;
        this.countilink = 0;
        this.countdlink = 0;
        this.ileftlink = null;
        this.irightlink = null;
        this.imidlink = null;
        this.dleftlink = null;
        this.dmidlink = null;
        this.drightlink = null;
        this.parent = null;
    }
    //부모노드 존재여부 확인
    public IndexNode getHasParent(){
        return this.parent;
    }
    //인덱스노드가 데이터링크를 가진 노드인지 체크
    public boolean getHasDataLink(){
        return this.hasdatalink;
    }
    //인덱스노드의 데이터링크 포함여부 설정
    public void setHasDataLink(boolean v){
        this.hasdatalink = v;
    }
    //분할된 인덱스노드 단어 null처리 하는 메소드
    public void setWordnull(){
        this.words[1] = null;
        this.words[2] = null;
    }
    //인덱스노드 단어 가져오기
    public String getWords(int selindex){
        return this.words[selindex];
    }
    //인덱스노드 단어 설정하기
    public void setWords(String newdata){
        if(this.words[1] == null) {
            if (this.words[0] == null) {
                this.words[0] = newdata;
                this.countwords++;
            } else if (this.words[0].compareTo(newdata) > 0) {
                String temp = this.words[0];
                this.words[0] = newdata;
                this.words[1] = temp;
                this.countwords++;
            } else if (this.words[0].compareTo(newdata) < 0) {
                this.words[1] = newdata;
                this.countwords++;
            }
        }else{
            if(this.words[0].compareTo(newdata) > 0){
                this.words[2] = this.words[1];
                this.words[1] = this.words[0];
                this.words[0] = newdata;
                this.countwords++;
            } else if(this.words[0].compareTo(newdata) < 0
                    && this.words[1].compareTo(newdata) > 0) {
                this.words[2] = this.words[1];
                this.words[1] = newdata;
                this.countwords++;
            }else if(this.words[1].compareTo(newdata) < 0){
                this.words[2] = newdata;
                this.countwords++;
            }
        }
    }
    //연결된 인덱스링크 갯수 가져오기
    public int getCountilink(){
        return this.countilink;
    }
    //연결된 데이터링크 갯수 가져오기
    public int getCountdlink(){
        return this.countdlink;
    }
    //저장된 키단어 갯수 가져오기
    public int getCountwords(){
        return this.countwords;
    }
    //인덱스링크 갯수 설정하기
    public void setCountilink(int num){
        this.countilink = num;
    }
    //데이터링크 갯수 설정하기
    public void setCountdlink(int num){
        this.countdlink = num;
    }
    //저장된 단어갯수 설정하기
    public void setCountwords(int num){
        this.countwords = num;
    }
    //단어갯수 +1
    public void countWordsPlus(){
        this.countwords++;
    }
    //데이터링크 갯수 +1
    public void countdlinkPlus(){
        this.countdlink++;
    }
    //인덱스링크 갯수 +1
    public void countilinkPlus(){
        this.countilink++;
    }
}
