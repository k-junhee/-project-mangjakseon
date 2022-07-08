package com.mangjakseon.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

//컬럼 데이터 생성 공식
public class MemberIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int randomNum = (int)(Math.floor(Math.random()*1000000));    // 0~999999의 6자리 숫자를 생성
        // 현재시간(1/1000 초)값과 랜덤생성된 숫자(6자리,빈자리는 0으로 채움)를 합쳐 문자열을 생성 후 리턴
        return System.currentTimeMillis()+ "-" + String.format("%06d",randomNum);
    }
}
