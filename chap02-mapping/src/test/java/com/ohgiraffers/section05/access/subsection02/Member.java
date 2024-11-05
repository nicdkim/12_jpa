package com.ohgiraffers.section05.access.subsection02;

import jakarta.persistence.*;

@Entity(name = "member_section05_subsection02")
@Table(name = "tbl_member_section05_subsection05")
@Access(AccessType.PROPERTY)
public class Member {

    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Id
    public int getMemberNo() {
        System.out.println("getMemberNo 를 이용한 access 확인");
        return memberNo;
    }

    public Member() {
    }

    public Member(int memberNo, String memberId, String memberPwd, String nickname) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

//    @Access(AccessType.PROPERTY)
    public String getMemberId() {
        System.out.println("getMemberId 를 사용한 access 확인");
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        System.out.println("getMemberPwd 를 사용한 access 확인");
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getNickname() {
        System.out.println("getNickname 를 사용한 access 확인");
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
