package test.example.demo.user.domain.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import test.example.demo.reservation.domain.entity.Reservation;
import test.example.demo.user.domain.enums.Role;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotBlank(message = "TC Kimlik numarası boş olamaz.")
	@Size(min = 11, max = 11, message = "TC Kimlik numarası 11 haneli olmalıdır.")
	@Column(name = "tc_number")
	private String tcNumber;
	
	@NotBlank(message = "Ad boş olamaz")
	private String name;
	
	@NotBlank(message = "Soyad boş olamaz")
	private String surname;
	
	@NotNull(message = "Doğum yılı boş olamaz.")
	private LocalDate birthyear;
	
	@NotBlank(message = "Email boş olamaz.")
	@Email(message = "Geçersiz email formatı.")
	@Column(unique = true,length = 100, nullable = false)
	private String email;
	
	@NotBlank(message = "Şifre boş olamaz.")
	@Size(min = 6, message = "Şifre en az 6 karakter olmalıdır.")
	private String password;
	
	@NotBlank(message = "Telefon numarası boş olamaz.")
	private String phoneNumber;
	
	private String address;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Reservation> reservations = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Role role = Role.USER;
	
	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

}
