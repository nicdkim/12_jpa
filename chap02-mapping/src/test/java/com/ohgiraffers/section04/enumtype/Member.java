package com.ohgiraffers.section04.enumtype;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

@Entity(name = "member_section04")
@Table(name = "tbl_member_section04")
public class Member {

    @Id
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "member_role")
    @Enumerated(EnumType.ORDINAL) // 숫자로 db에 들어감
    // @Enumerated(EnumType.STRING) 설정된 상수 필드의 이름으로 db에 들어감
    private RoleType memberRole;

    public Member() {
    }

    public Member(int memberNo, String memberId, String memberPwd, String nickname, RoleType memberRole) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
        this.memberRole = memberRole;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public RoleType getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(RoleType memberRole) {
        this.memberRole = memberRole;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", memberRole=" + memberRole +
                '}';
    }
}
