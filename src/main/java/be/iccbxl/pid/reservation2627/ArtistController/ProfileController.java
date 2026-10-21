package be.iccbxl.pid.reservation2627.ArtistController;
import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.reservation2627.dto.UserProfileDto;
import be.iccbxl.pid.reservation2627.model.Language;
import be.iccbxl.pid.reservation2627.model.User;
import be.iccbxl.pid.reservation2627.repository.UserRepository;
import be.iccbxl.pid.reservation2627.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;

    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // Afficher la page de profil
    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        User user = userRepository.findByLogin(login);

        if(user==null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // Conversion User → UserProfileDto
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setLangue(user.getLangue());
        dto.setLogin(user.getLogin());
        dto.setRole(user.getRole().getValue());

        // Conversion du code linguistique en nom de la langue
        Language userLanguage = Arrays.stream(Language.values())
                .filter(lang -> user.getLangue() != null && lang.toString().equals(user.getLangue().toUpperCase()))
                .findFirst().orElse(null);

        model.addAttribute("user_language", userLanguage!=null ? userLanguage.getDescription() : "Inconnue");
        model.addAttribute("user", dto);
        return "authentication/profile";
    }

    // Enregistrer les modifications
    @PostMapping(value = "/profile", params = {"edit"})
    public String updateProfile(
            @Valid @ModelAttribute("user") UserProfileDto dto,
            BindingResult result, Model model, RedirectAttributes redirAttrs) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Erreurs de validation !");
            return "authentication/profile";
        }

        // Sécurité : on modifie toujours le profil de l'utilisateur connecté,
        // sans faire confiance à l'id du champ caché
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepository.findByLogin(login);
        dto.setId(current.getId());

        userService.updateUserFromDto(dto);
        redirAttrs.addFlashAttribute("successMessage", "Profil mis à jour avec succès !");
        return "redirect:/profile";
    }
    @DeleteMapping("/profile/delete")
    public String deleteAccount(HttpServletRequest request, HttpServletResponse response,
                                RedirectAttributes redirAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        if (request.isUserInRole("ADMIN")) {
            redirAttrs.addFlashAttribute("errorMessage", "Pas de suppression de son propre compte admin !");
            return "redirect:/profile";
        }

        // Supprimer l'utilisateur courant
        userService.deleteByLogin(login);

        // Déconnecter l'utilisateur et invalider la session
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        SecurityContextHolder.clearContext();

        redirAttrs.addFlashAttribute("successMessage", "Votre compte a été supprimé avec succès.");
        return "redirect:/";
    }
}