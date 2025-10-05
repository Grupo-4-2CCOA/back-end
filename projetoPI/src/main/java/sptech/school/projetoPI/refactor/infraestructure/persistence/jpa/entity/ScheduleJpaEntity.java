//package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import sptech.school.projetoPI.old.core.enums.Status;
//import sptech.school.projetoPI.old.infrastructure.persistence.entity.ClientJpaEntity;
//import sptech.school.projetoPI.old.infrastructure.persistence.entity.EmployeeJpaEntity;
//import sptech.school.projetoPI.old.infrastructure.persistence.entity.PaymentTypeJpaEntity;
//import sptech.school.projetoPI.refactor.core.domain.enumerator.ScheduleStatus;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "schedule")
//public class ScheduleJpaEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Status status;
//
//    @Column(name = "appointment_datetime", nullable = false)
//    private LocalDateTime appointmentDatetime;
//
//    @Column(name = "duration")
//    private Integer duration;
//
//    @Column(name = "transaction_hash", length = 255)
//    private String transactionHash;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_client", nullable = false)
//    private ClientJpaEntity client;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_employee", nullable = false)
//    private EmployeeJpaEntity employee;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_payment_type")
//    private PaymentTypeJpaEntity paymentType;
//
//    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Set<ScheduleItemJpaEntity> items = new HashSet<>();
//}
