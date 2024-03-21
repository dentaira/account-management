package dentaira.accountmanagement.member.infra;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.common.EntityUpdateConflictException;
import dentaira.accountmanagement.jooq.MembersRecord;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.member.MemberStatus;
import dentaira.accountmanagement.member.domain.Member;
import dentaira.accountmanagement.member.domain.MemberRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static dentaira.accountmanagement.jooq.Tables.MEMBERS;

@Repository
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private static final TimeBasedEpochGenerator generator = Generators.timeBasedEpochGenerator();
    private final DSLContext context;

    @Override
    public MemberId generateId() {
        return new MemberId(generator.generate());
    }

    @Override
    public Optional<Member> findById(MemberId memberId) {
        var record = context.selectFrom(MEMBERS).where(MEMBERS.MEMBER_ID.eq(memberId.value())).fetchOptional();
        return record.map(MemberRepositoryImpl::toMember);
    }

    @Override
    public void save(Member member) {
        context.insertInto(MEMBERS)
                .set(MEMBERS.MEMBER_ID, member.memberId().value())
                .set(MEMBERS.COMPANY_NAME, member.companyName())
                .set(MEMBERS.DEPARTMENT_NAME, member.departmentName())
                .set(MEMBERS.STATUS, member.status().name())
                .set(MEMBERS.EMAIL, member.email().value())
                .set(MEMBERS.VERSION, member.version())
                .set(MEMBERS.CREATED_AT, member.createdAt())
                .set(MEMBERS.UPDATED_AT, member.updatedAt())
                .execute();
    }

    @Override
    public Member update(Member member) {
        return context.update(MEMBERS)
                .set(MEMBERS.COMPANY_NAME, member.companyName())
                .set(MEMBERS.DEPARTMENT_NAME, member.departmentName())
                .set(MEMBERS.STATUS, member.status().name())
                .set(MEMBERS.EMAIL, member.email().value())
                .set(MEMBERS.VERSION, member.version() + 1)
                .set(MEMBERS.CREATED_AT, member.createdAt())
                .set(MEMBERS.UPDATED_AT, member.updatedAt())
                .where(MEMBERS.MEMBER_ID.eq(member.memberId().value()))
                .and(MEMBERS.VERSION.eq(member.version()))
                .returning()
                .fetchOptional()
                .map(MemberRepositoryImpl::toMember)
                .orElseThrow(EntityUpdateConflictException::new);
    }

    private static Member toMember(MembersRecord record) {
        return new Member(
                new MemberId(record.getMemberId()),
                record.getCompanyName(),
                record.getDepartmentName(),
                MemberStatus.valueOf(record.getStatus()),
                new EmailAddress(record.getEmail()),
                record.getVersion(),
                record.getCreatedAt(),
                record.getUpdatedAt()
        );
    }
}
