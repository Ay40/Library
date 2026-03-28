package controller;

import dao.MemberDAO;
import model.Member;

import java.util.List;

public class MemberController {

    private MemberDAO dao = new MemberDAO();

    public void addMember(String name, String email, String phone, String address, String status) {
        Member m = new Member(0, name, email, phone, address, null, status);
        dao.addMember(m);
    }
    
    public void updateMember(int id, String name, String email, String phone, String address, String status) {

        Member m = new Member(id, name, email, phone, address, null, status);
        dao.updateMember(m);
    }
    
    public void deleteMember(int id) {
        dao.deleteMember(id);
    }

    public List<Member> getMembers() {
        return dao.getAllMembers();
    }
}