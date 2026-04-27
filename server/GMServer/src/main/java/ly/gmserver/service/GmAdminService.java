package ly.gmserver.service;

import ly.db.entry.GmAdminEntry;
import ly.db.entry.GmAdminEntryHelper;
import ly.db.entry.GmRoleEntry;
import ly.db.entry.GmRoleEntryHelper;
import ly.gmserver.dto.AdminVO;
import ly.gmserver.dto.LoginRequest;
import ly.gmserver.dto.LoginResponse;
import ly.gmserver.dto.PageResult;
import ly.gmserver.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GmAdminService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public GmAdminService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        List<GmAdminEntry> admins = GmAdminEntryHelper.select(new String[]{"username"}, request.getUsername());
        if (admins.isEmpty()) {
            return null;
        }
        GmAdminEntry admin = admins.get(0);
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return null;
        }
        if (admin.getStatus() == 0) {
            return null; // disabled
        }
        String token = jwtUtil.createToken(admin.getId(), admin.getUsername());
        AdminVO vo = toVO(admin);
        return new LoginResponse(token, vo);
    }

    public PageResult<AdminVO> list(int page, int pageSize, String keyword) {
        List<GmAdminEntry> all = GmAdminEntryHelper.select(null);
        // simple filtering
        List<GmAdminEntry> filtered = all;
        if (keyword != null && !keyword.isEmpty()) {
            filtered = all.stream()
                .filter(a -> a.getUsername().contains(keyword))
                .collect(Collectors.toList());
        }
        int total = filtered.size();
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, total);
        List<GmAdminEntry> pageList = from < total ? filtered.subList(from, to) : new ArrayList<>();
        List<AdminVO> vos = pageList.stream().map(this::toVO).collect(Collectors.toList());
        return new PageResult<>(vos, total);
    }

    public AdminVO getById(Long id) {
        GmAdminEntry entry = GmAdminEntryHelper.getGmAdminEntryById(id);
        return entry != null ? toVO(entry) : null;
    }

    public boolean create(String username, String password, Integer roleId) {
        List<GmAdminEntry> exist = GmAdminEntryHelper.select(new String[]{"username"}, username);
        if (!exist.isEmpty()) return false;
        GmAdminEntry entry = new GmAdminEntry();
        entry.setUsername(username);
        entry.setPassword(passwordEncoder.encode(password));
        entry.setRoleId(roleId != null ? roleId : 2);
        entry.setStatus((byte) 1);
        return GmAdminEntryHelper.save(entry);
    }

    public boolean update(Long id, String username, Integer roleId, Byte status) {
        GmAdminEntry entry = GmAdminEntryHelper.getGmAdminEntryById(id);
        if (entry == null) return false;
        if (username != null && !username.isEmpty()) entry.setUsername(username);
        if (roleId != null) entry.setRoleId(roleId);
        if (status != null) entry.setStatus(status);
        return GmAdminEntryHelper.update(entry, "username", "role_id", "status");
    }

    public boolean resetPassword(Long id, String newPassword) {
        GmAdminEntry entry = GmAdminEntryHelper.getGmAdminEntryById(id);
        if (entry == null) return false;
        entry.setPassword(passwordEncoder.encode(newPassword));
        return GmAdminEntryHelper.update(entry, "password");
    }

    public boolean delete(Long id) {
        GmAdminEntry entry = GmAdminEntryHelper.getGmAdminEntryById(id);
        if (entry == null) return false;
        return GmAdminEntryHelper.delete(entry);
    }

    private AdminVO toVO(GmAdminEntry entry) {
        AdminVO vo = new AdminVO();
        vo.setId(entry.getId());
        vo.setUsername(entry.getUsername());
        vo.setRoleId(entry.getRoleId());
        vo.setStatus(entry.getStatus() != null ? entry.getStatus().intValue() : null);
        vo.setCreateTime(entry.getCreateTime());
        vo.setUpdateTime(entry.getUpdateTime());
        // load role name
        GmRoleEntry role = GmRoleEntryHelper.getGmRoleEntryById(entry.getRoleId());
        vo.setRoleName(role != null ? role.getName() : "Unknown");
        return vo;
    }
}
